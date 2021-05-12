package com.github.springbootjunittest.thread;

import com.github.springbootjunittest.mythread.SemaphoreTask;
import com.github.springbootjunittest.mythread.ThreadPoolUtil;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenjianhua
 * @date 2020/9/10
 */
public class SemaphoreTest {
    private static int threadCount = 20;

    @Test
    public void SemaphoreTest() throws Exception {
        AtomicInteger count = new AtomicInteger();
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        // 资源数目
        Semaphore semaphore = new Semaphore(2);
        for (int i = 0; i < threadCount; i++) {
            ThreadPoolUtil.getInstance().submit(new SemaphoreTask(semaphore, countDownLatch, i));
        }
        countDownLatch.await();
    }
}
