package com.github.springbootjunittest.leetcode;

import com.github.springbootjunittest.mythread.ThreadPoolUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author chenjianhua
 * @date 2020/9/14
 */
@Slf4j
public class Code1115PrintFooBarAlternatelyTest {
    private final static String Love = "love";
    private final static String Fang = "fang";

    private final static Integer num = 3;

    public void test(PrintFooBarAlternately1115 printFooBarAlternately) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(2 * num);
        BlockingQueue<String> blockingQueue = new LinkedBlockingQueue(2 * num);
        ThreadPoolUtil.getInstance().submit(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                printFooBarAlternately.foo(new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        TimeUnit.MILLISECONDS.sleep(500);
                        blockingQueue.add(Love);
                        log.info(Love);
                        countDownLatch.countDown();
                    }
                });
            }
        });
        ThreadPoolUtil.getInstance().submit(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                printFooBarAlternately.bar(new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        TimeUnit.MILLISECONDS.sleep(500);
                        blockingQueue.add(Fang);
                        log.info(Fang);
                        countDownLatch.countDown();
                    }
                });
            }
        });
        countDownLatch.await();
        for (int i = 0; i < num; i++) {
            String value = blockingQueue.take();
            assertEquals(Love, value);
            value = blockingQueue.take();
            assertEquals(Fang, value);
        }
    }

    @Test
    public void PrintFooBarAlternatelyCyclicBarrierTest() throws Exception {
        PrintFooBarAlternatelyCyclicBarrier printFooBarAlternatelyCyclicBarrier = new PrintFooBarAlternatelyCyclicBarrier(num);
        test(printFooBarAlternatelyCyclicBarrier);
    }

    @Test
    public void PrintFooBarAlternatelyReentrantLockTest() throws Exception {
        PrintFooBarAlternatelyReentrantLock printFooBarAlternatelyReentrantLock = new PrintFooBarAlternatelyReentrantLock(num);
        test(printFooBarAlternatelyReentrantLock);
    }

    @Test
    public void PrintFooBarAlternatelyBlockingQueueTest() throws Exception {
        PrintFooBarAlternatelyBlockingQueue printFooBarAlternatelyBlockingQueue = new PrintFooBarAlternatelyBlockingQueue(num);
        test(printFooBarAlternatelyBlockingQueue);
    }

    @Test
    public void PrintFooBarAlternatelySynchronizedTest() throws Exception {
        PrintFooBarAlternatelySynchronized printFooBarAlternatelySynchronized = new PrintFooBarAlternatelySynchronized(num);
        test(printFooBarAlternatelySynchronized);
    }
}
