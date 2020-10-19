package com.github.springbootjunittest;

import com.github.springbootjunittest.mythread.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author chenjianhua
 * @date 2020/10/19
 * ThreadLocal 是 JDK底层提供的一个解决多线程并发问题的工具类,
 * 它为每个线程提供了一个本地的副本变量机制，实现了和其它线程隔离，并且这种变量只在本线程的生命周期内起作用，
 * 可以减少同一个线程内多个方法之间的公共变量传递的复杂度。
 */
@Slf4j
public class ThreadLocalTest {
    // 为不同的线程管理连接
    private static ThreadLocal<Integer> local = new ThreadLocal<>();

    private static int threadCount = 20;

    public static final byte[] allocateMem() {
        // 这里分配一个1M的对象
        byte[] b = new byte[1024 * 1024 * 100];
        return b;
    }

    @Test
    public void ThreadLocalTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            final Integer index = i;
            ThreadPoolUtil.getInstance().submit(() -> {
                Integer before = local.get();
                local.set(index);
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Integer nowLocal = local.get();
                assertEquals(nowLocal, index);
                log.info("nowLocal:{},before:{},index:{}", nowLocal, before, index);
                //local.remove();
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
    }
}
