package com.github.spring.boot.idleaf.service.idleaf;

import com.github.id.util.UUIDUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author chenjianhua
 * @date 2020/9/24
 */
@Slf4j
@Component
public class RedisServer {

    private final static int DefaultRetryCount = 5;
    private final static int DefaultRetryTimeout = 300;
    private final static String luaScript = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    /**
     * redisLock前缀
     */
    private final static String REDIS_LOCK_PREFIX = "redisLock:";

    private RedisTemplate<String, String> redisTemplate;

    private ValueOperations<String, String> stringOperations;

    private SetOperations<String, String> setOperations;

    @Autowired
    public RedisServer(RedisTemplate<String, String> redisTemplate) {
        this.stringOperations = redisTemplate.opsForValue();
        this.setOperations = redisTemplate.opsForSet();
        this.redisTemplate = redisTemplate;
    }

    public Long setAdd(String key, String[] vs) {
        return setOperations.add(key, vs);
    }

    public Long setRemove(String key, String[] vs) {
        return setOperations.remove(key, vs);
    }

    public Set<String> setGet(String key) {
        return setOperations.members(key);
    }

    /**
     * 设置值
     *
     * @param timeout 过期时间（s）
     */
    public void set(String key, String value, long timeout) {
        stringOperations.set(key, value, timeout, TimeUnit.SECONDS);
    }

    public void set(String key, String value) {
        stringOperations.set(key, value);
    }

    public String get(String key) {
        return stringOperations.get(key);
    }

    public void delete(String key) {
        stringOperations.getOperations().delete(key);
    }

    public List<String> mGet(List<String> keys) {
        return stringOperations.multiGet(keys);
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
        String value = UUIDUtil.getUUID32();

        for (int i = 0; i < DefaultRetryCount; i++) {
            RedisSession redisSession = tryLock(key, value, ttlSecond);
            if (redisSession.getLockStatus()) {
                return redisSession;
            }
            log.info("{}获取锁失败，睡眠{}ms", Thread.currentThread().getName(), DefaultRetryTimeout);
            try {
                TimeUnit.MILLISECONDS.sleep(DefaultRetryTimeout);
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
            redisScript.setScriptText(luaScript);
            redisScript.setResultType(Long.class);
            redisTemplate.execute(redisScript, Collections.singletonList(redisSession.getKey()), redisSession.getValue());
        }
    }
}
