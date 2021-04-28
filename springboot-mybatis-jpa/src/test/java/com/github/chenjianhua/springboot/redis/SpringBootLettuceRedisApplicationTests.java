package com.github.chenjianhua.springboot.redis;


import com.github.chenjianhua.common.redis.support.RedisStringTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * @author chenjianhua
 * @date 2020-09-07 15:41:49
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("dev")
public class SpringBootLettuceRedisApplicationTests {

    @Autowired
    private RedisStringTemplate redisStringTemplate;

    @Test
    public void redisTest() {
        // 测试线程安全
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        String testIdKey = "test_userId";
        String testName = "test_name";
        //主键生成
        redisStringTemplate.set(testIdKey, "10000", 100);
        IntStream.range(0, 100).forEach(i ->
                executorService.execute(() -> redisStringTemplate.increment(testIdKey))
        );
        // 简单key value获取
        String userId = redisStringTemplate.get(testIdKey);
        log.info("[主键生成userId] - [{}]", userId);
        redisStringTemplate.set(testName, "", 100);
        String name = redisStringTemplate.get(testName);
        log.info("[字符缓存结果] - [{}]", name);
        //  以下只演示整合，具体Redis命令可以参考官方文档，Spring Data Redis 只是改了个名字而已，Redis支持的命令它都支持
        String userIdKey = "testUser:" + userId;
        redisStringTemplate.set(userIdKey, "fangfang", 100);
        // 对应 String（字符串）
        String fangName = redisStringTemplate.get(userIdKey);
        log.info("[对象缓存结果] - [{}]", fangName);
    }
}
