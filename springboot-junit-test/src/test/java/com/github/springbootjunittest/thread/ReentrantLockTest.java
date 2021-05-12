package com.github.springbootjunittest.thread;

import com.github.springbootjunittest.mythread.ThreadPoolUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author chenjianhua
 * @date 2021/5/12
 */
public class ReentrantLockTest {
    /**
     * volatile 解决多线程内存不可见问题。对于一写多读，是可以解决变量同步问题，
     * 但是如果多写，同样无法解决线程安全问题。
     */
    private static int threadCount = 20;
    private static int incrementNum = 1000;
    private volatile int num = 0;
    private volatile int tryLockNum = 0;

    @Test
    @DisplayName("ReentrantLock 等待锁")
    public void reentrantLockTest() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        ReentrantLock reentrantLock = new ReentrantLock();
        for (int i = 0; i < threadCount; i++) {
            ThreadPoolUtil.getInstance().submit(() -> {
                for (int j = 0; j < incrementNum; j++) {
                    reentrantLock.lock();
                    num++;
                    reentrantLock.unlock();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        assertEquals((threadCount * incrementNum), num);
    }

    @Test
    @DisplayName("ReentrantLock tryLock尝试获取锁")
    public void reentrantLockTryLockTest() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        ReentrantLock reentrantLock = new ReentrantLock();
        for (int i = 0; i < threadCount; i++) {
            ThreadPoolUtil.getInstance().submit(() -> {
                for (int j = 0; j < incrementNum; ) {
                    // 尝试获取锁
                    if (reentrantLock.tryLock()) {
                        tryLockNum++;
                        reentrantLock.unlock();
                        j++;
                    } else {
                        // 获取锁失败
                    }
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        assertEquals((threadCount * incrementNum), tryLockNum);
    }
}
