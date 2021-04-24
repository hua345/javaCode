package com.github.chenjianhua.common.redis.support;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author chenjianhua
 * @date 2020-09-07 15:41:49
 */
@Component
public class RedisStringTemplate {

    RedisTemplate<String, String> redisTemplate;

    ValueOperations<String, String> stringOperations;

    @Autowired
    public RedisStringTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringOperations = redisTemplate.opsForValue();
    }

    public void set(String key, String value, long timeout, TimeUnit timeUnit) {
        stringOperations.set(key, value, timeout, timeUnit);
    }

    public void set(String key, String value, long timeout) {
        stringOperations.set(key, value, timeout, TimeUnit.SECONDS);
    }

    public String get(String key) {
        return stringOperations.get(key);
    }

    public List<String> multiGet(List<String> keys) {
        return stringOperations.multiGet(keys);
    }

    public Boolean delete(String key) {
        return stringOperations.getOperations().delete(key);
    }

    public Long delete(Collection<String> keys) {
        return stringOperations.getOperations().delete(keys);
    }

    public Boolean setIfAbsent(String key, String value, long ttlSecond) {
        return stringOperations.setIfAbsent(key, value, ttlSecond, TimeUnit.SECONDS);
    }

    public <M> M scriptExecute(RedisScript<M> script, List<String> keys, Object... args) {
        return redisTemplate.execute(script, keys, args);
    }

    public Long increment(String key) {
        return stringOperations.increment(key);
    }
}
