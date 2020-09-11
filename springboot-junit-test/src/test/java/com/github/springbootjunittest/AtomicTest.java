package com.github.springbootjunittest;

import com.github.springbootjunittest.mythread.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.atomic.LongAdder;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author chenjianhua
 * @date 2020/9/9 21:17
 */
@Slf4j
public class AtomicTest {
    /**
     * volatile 解决多线程内存不可见问题。对于一写多读，是可以解决变量同步问题，
     * 但是如果多写，同样无法解决线程安全问题。
     */
    private static int threadCount = 20;
    private static int incrementNum = 100000;
    private volatile int num = 1;

    @Test
    public void VolatileTest() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            ThreadPoolUtil.getInstance().submit(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < incrementNum; j++) {
                        num++;
                    }
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        assertTrue((threadCount * incrementNum) > num);
    }

    @Test
    public void AtomicTest() throws Exception {
        AtomicInteger count = new AtomicInteger();
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            ThreadPoolUtil.getInstance().submit(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < incrementNum; j++) {
                        count.incrementAndGet();
                    }
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        assertEquals(threadCount * incrementNum, count.get());
    }

    @Test
    public void LongAdderTest() throws Exception {
        LongAdder longAdder = new LongAdder();
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            ThreadPoolUtil.getInstance().submit(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < incrementNum; j++) {
                        longAdder.increment();
                    }
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        assertEquals(threadCount * incrementNum, longAdder.sum());
    }

    @Test
    public void AtomicStampedReferenceTest() {
        AtomicStampedReference<String> asr = new AtomicStampedReference<>("A", 0);
        ThreadPoolUtil.getInstance().submit(new Runnable() {
            @Override
            public void run() {
                int currentStamp = asr.getStamp();
                String currentReference = asr.getReference();
                boolean result = asr.compareAndSet(currentReference, "B", currentStamp, currentStamp + 1);
                assertEquals("B", asr.getReference());
                assertEquals(true, result);
                assertEquals(currentStamp + 1, asr.getStamp());
            }
        });
        ThreadPoolUtil.getInstance().submit(new Runnable() {
            @Override
            public void run() {
                int currentStamp = asr.getStamp();
                String currentReference = asr.getReference();
                boolean result = asr.compareAndSet(currentReference, "A", 0, 0);
                assertEquals("B", asr.getReference());
                assertEquals(false, result);
                assertEquals(1, asr.getStamp());
                result = asr.compareAndSet(currentReference, "A", currentStamp, currentStamp + 1);
                assertEquals("A", asr.getReference());
                assertEquals(true, result);
                assertEquals(currentStamp + 1, asr.getStamp());
            }
        });
    }

    @Test
    public void AtomicStampedReferenceTest2() throws Exception {
        AtomicStampedReference<Integer> asr = new AtomicStampedReference<>(1, 1);

        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            ThreadPoolUtil.getInstance().submit(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < incrementNum; j++) {
                        int currentStamp = asr.getStamp();
                        Integer currentReference = asr.getReference();
                        asr.compareAndSet(currentReference, currentReference + 1, currentStamp, currentStamp + 1);
                    }
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        assertEquals(asr.getReference(), asr.getStamp());
        assertTrue((threadCount * incrementNum) > asr.getReference());
    }
}
