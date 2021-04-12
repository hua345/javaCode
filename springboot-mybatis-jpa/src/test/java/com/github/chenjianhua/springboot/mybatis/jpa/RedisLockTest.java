package com.github.chenjianhua.springboot.mybatis.jpa;

import com.github.chenjianhua.springboot.mybatis.jpa.service.RedisLockService;
import com.github.common.util.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author chenjianhua
 * @date 2020/9/24
 */
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class RedisLockTest {
    @Autowired
    private RedisLockService redisLockService;

    @Autowired
    private RedisTemplate<String, Serializable> redisCacheTemplate;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final static String luaScript = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    private final static String prefixName = "lock:";
    private final static String testName = "fang";
    private final static String testNameValue = "fang";
    private final static Integer threadNum = 100;
    private final static Long ttlSecond = 10L;

    @Test
    public void testLua() {
        DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();
        // 设置脚本
        redisScript.setScriptText("return 'Hello Redis'");
        // 定义返回类型。注意：如果没有这个定义，spring 不会返回结果
        redisScript.setResultType(String.class);
        RedisSerializer<String> stringRedisSerializer = redisCacheTemplate.getStringSerializer();
        String luaResult = redisCacheTemplate.execute(redisScript, stringRedisSerializer, stringRedisSerializer, null);
        Assert.assertEquals("Hello Redis", luaResult);
    }

    @Test
    public void testLua2() {
        redisTemplate.opsForValue().set(testName, testNameValue);

        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(luaScript);
        redisScript.setResultType(Long.class);
        RedisSerializer<String> stringRedisSerializer = redisTemplate.getStringSerializer();
        Long luaResult = redisTemplate.execute(redisScript, Collections.singletonList(testName), testNameValue);
        Long expected = 1L;
        Assert.assertEquals(expected, luaResult);
    }

    /**
     * 测试key一样时锁情况
     */
    @Test
    public void RedisLockTest() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(threadNum);
        AtomicInteger result = new AtomicInteger();
        IntStream.range(0, threadNum).forEach(i ->
                ThreadPoolUtil.getInstance().submit(() -> {
                    RedisLockService.RedisSession redisSession = redisLockService.tryLock(prefixName + testName + i, ttlSecond);
                    if (redisSession.getLockStatus()) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        result.getAndIncrement();
                        redisLockService.releaseLock(redisSession);
                    }
                    latch.countDown();
                })
        );
        latch.await();
        Assert.assertEquals(threadNum.intValue(), result.intValue());
    }

    /**
     * 测试key不一样时锁情况
     */
    @Test
    public void RedisLockTest2() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(threadNum);
        AtomicInteger result = new AtomicInteger();
        IntStream.range(0, threadNum).forEach(i ->
                ThreadPoolUtil.getInstance().submit(() -> {
                    RedisLockService.RedisSession redisSession = redisLockService.tryLock(prefixName + testName + i, ttlSecond);
                    if (redisSession.getLockStatus()) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        result.getAndIncrement();
                        redisLockService.releaseLock(redisSession);
                    }
                    latch.countDown();
                })
        );
        latch.await();
        Assert.assertEquals(threadNum.intValue(), result.intValue());
    }

    /**
     * 测试key相同2到5次时锁情况
     */
    @Test
    public void RedisLockTest3() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(threadNum * 2);
        AtomicInteger result = new AtomicInteger();
        IntStream.range(0, threadNum).forEach(i -> {
            IntStream.range(0, 2).forEach(j -> {
                ThreadPoolUtil.getInstance().submit(() -> {
                    RedisLockService.RedisSession redisSession = redisLockService.tryLock(prefixName + testName + i, ttlSecond);
                    if (redisSession.getLockStatus()) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        result.getAndIncrement();
                        redisLockService.releaseLock(redisSession);
                    }
                    latch.countDown();
                });
            });
        });
        latch.await();
        Assert.assertEquals(threadNum.intValue() * 2, result.intValue());
    }
}
