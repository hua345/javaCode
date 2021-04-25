package com.github.chenjianhua.common.redis.config;

import com.github.chenjianhua.common.redis.support.RedisLockService;
import com.github.chenjianhua.common.redis.support.RedisStringTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

/**
 * @author chenjianhua
 * @date 2020-09-07 15:41:49
 */
@Slf4j
@Configuration
public class RedisConfig {
    /**
     * LettuceConnectionFactory redisConnectionFactory
     *
     * @param lettuceConnectionFactory lettuce连接池
     * @return redisTemplate
     */
    @Bean
    public RedisTemplate<String, Serializable> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        log.info("redis初始化成功");
        return redisTemplate;
    }

    /**
     * 覆盖默认的RedisProperties
     */
    @Bean
    @Primary
    public RedisProperties redisProperties(RedisProperties redisProperties) {
        if (null != redisProperties && null != redisProperties.getLettuce() && redisProperties.getLettuce().getPool().getMinIdle() <= 0) {
            redisProperties.getLettuce().getPool().setMinIdle(5);
        }
        log.info("redis最小连接数:{}", redisProperties.getLettuce().getPool().getMinIdle());
        return redisProperties;
    }
}
