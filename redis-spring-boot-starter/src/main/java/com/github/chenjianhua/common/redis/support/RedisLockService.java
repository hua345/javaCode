package com.github.chenjianhua.common.redis.support;

import com.github.chenjianhua.common.redis.util.UuidUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author chenjianhua
 * @date 2020/9/24
 */
@Slf4j
@Service
public class RedisLockService {

    private final static int DEFAULT_RETRY_COUNT = 5;
    private final static int DEFAULT_RETRY_TIMEOUT = 500;
    private final static String LUA_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    /**
     * redisLock前缀
     */
    private final static String REDIS_LOCK_PREFIX = "redisLock:";

    private RedisTemplate<String, String> redisTemplate;

    private ValueOperations<String, String> stringOperations;

    @Autowired
    public RedisLockService(RedisTemplate<String, String> redisTemplate) {
        this.stringOperations = redisTemplate.opsForValue();
        this.redisTemplate = redisTemplate;
    }

    @Data
    public static class RedisSession {
        private String key;
        private String value;
        private Boolean lockStatus;
    }

    /**
     * 获得锁
     */
    public RedisSession tryLock(String key, long ttlSecond) {
        String value = UuidUtil.getUuid32();

        for (int i = 0; i < DEFAULT_RETRY_COUNT; i++) {
            RedisSession redisSession = tryLock(key, value, ttlSecond);
            if (redisSession.getLockStatus()) {
                return redisSession;
            }
            log.info("{}获取锁失败，睡眠{}ms", Thread.currentThread().getName(), DEFAULT_RETRY_TIMEOUT);
            try {
                TimeUnit.MILLISECONDS.sleep(DEFAULT_RETRY_TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        RedisSession redisSession = new RedisSession();
        redisSession.setLockStatus(false);
        return redisSession;
    }

    /**
     * 获得锁
     */
    private RedisSession tryLock(String key, String value, long ttlSecond) {
        key = REDIS_LOCK_PREFIX + key;
        log.info("{}获取redis分布式锁,key:{} value:{} ttlSecond:{}", Thread.currentThread().getName(), key, value, ttlSecond);
        Boolean lockStatus = stringOperations.setIfAbsent(key, value, ttlSecond, TimeUnit.SECONDS);
        RedisSession redisSession = new RedisSession();
        redisSession.setLockStatus(lockStatus);
        if (lockStatus) {
            redisSession.setKey(key);
            redisSession.setValue(value);
        }
        return redisSession;
    }


    public void releaseLock(RedisSession redisSession) {
        if (null != redisSession && redisSession.getLockStatus()) {
            DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
            redisScript.setScriptText(LUA_SCRIPT);
            redisScript.setResultType(Long.class);
            redisTemplate.execute(redisScript, Collections.singletonList(redisSession.getKey()), redisSession.getValue());
        }
    }
}
