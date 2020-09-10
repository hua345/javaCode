package com.github.springbootjunittest;

import com.github.springbootjunittest.mythread.ThreadPoolUtil;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.atomic.LongAdder;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author chenjianhua
 * @date 2020/9/9 21:17
 */
public class AtomicTest {
    private static int threadCount = 20;
    private static int incrementNum = 100000;
    StopWatch stopWatch = new StopWatch();

    @Test
    public void AtomicTest() throws Exception {
        AtomicInteger count = new AtomicInteger();
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        stopWatch.start("AtomicInteger");
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
        stopWatch.stop();
        assertEquals(threadCount * incrementNum, count.get());
        System.out.printf("AtomicInteger自增次数:%d 耗时:%dms", threadCount * incrementNum, stopWatch.getTotalTimeMillis());
    }

    @Test
    public void LongAdderTest() throws Exception {
        LongAdder longAdder = new LongAdder();
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        stopWatch.start("LongAdder");
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
        stopWatch.stop();
        assertEquals(threadCount * incrementNum, longAdder.sum());
        System.out.printf("LongAdder自增次数:%d 耗时:%dms", threadCount * incrementNum, stopWatch.getTotalTimeMillis());
    }

    public void printCurrentCas(AtomicStampedReference<String> asr) {
        System.out.println(Thread.currentThread().getName() + "当前变量值=" + asr.getReference() + "当前版本戳=" + asr.getStamp());
    }

    public void printCasResult(AtomicStampedReference<String> asr, boolean result) {
        System.out.println(Thread.currentThread().getName() + "当前变量值=" + asr.getReference() + "当前版本戳=" + asr.getStamp() + "更新成功?" + result);
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
        System.out.println("执行次数:" + threadCount * incrementNum + ",成功次数:" + asr.getReference());
    }
}
