package com.github.spring.boot.idleaf;


import com.github.spring.boot.idleaf.utils.ThreadPoolUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

/**
 * @author chenjianhua
 * @date 2020-09-01 16:46:26
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootLettuceRedisApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(SpringBootLettuceRedisApplicationTests.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String, Serializable> redisCacheTemplate;

    private final static String testIdKey = "test_userId";
    private final static String testName = "test_name";
    private final static Integer testIdValue = 10000;
    private final static Integer testNum = 100;
    private final static String testNameValue = "fang";

    @Test
    public void redisTest() throws InterruptedException {
        // 测试线程安全
        //主键生成
        stringRedisTemplate.opsForValue().set(testIdKey, testIdValue.toString());
        CountDownLatch latch = new CountDownLatch(testNum);
        IntStream.range(0, testNum).forEach(i ->
                ThreadPoolUtil.getInstance().submit(() -> {
                    stringRedisTemplate.opsForValue().increment(testIdKey, 1);
                    latch.countDown();
                })
        );
        latch.await();
        // 简单key value获取
        String userId = stringRedisTemplate.opsForValue().get(testIdKey);
        Integer expectedId = testIdValue + testNum;
        Assert.assertEquals(expectedId, Integer.valueOf(userId));
        stringRedisTemplate.opsForValue().set(testName, testNameValue);
        String name = stringRedisTemplate.opsForValue().get(testName);
        Assert.assertEquals(testNameValue, name);
        //  以下只演示整合，具体Redis命令可以参考官方文档，Spring Data Redis 只是改了个名字而已，Redis支持的命令它都支持
        String userIdKey = "testUser:" + userId;
        redisCacheTemplate.opsForValue().set(userIdKey, testNameValue);
        // 对应 String（字符串）
        String fangName = (String) redisCacheTemplate.opsForValue().get(userIdKey);
        Assert.assertEquals(testNameValue, fangName);

    }

}
