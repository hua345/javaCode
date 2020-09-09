package com.github.springbootjunittest;

import com.github.springbootjunittest.mythread.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author chenjianhua
 * @date 2020/9/9 21:17
 */
public class AtomicTest {
    @Test
    public void AtomicTest() throws Exception {
        int threadCount = 20;
        int incrementNum = 10000;
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
        assertEquals(count.get(), threadCount * incrementNum);
    }

    public void printAtomicStampedReference(AtomicStampedReference<String> asr) {
        System.out.println(Thread.currentThread().getName() + "当前变量值=" + asr.getReference() + "当前版本戳=" + asr.getStamp());
    }

    @Test
    public void AtomicStampedReferenceTest() {
        AtomicStampedReference<String> asr = new AtomicStampedReference<>("A", 0);
        printAtomicStampedReference(asr);
        ThreadPoolUtil.getInstance().submit(new Runnable() {
            @Override
            public void run() {
                int currentStamp = asr.getStamp();
                String currentReference = asr.getReference();
                System.out.println(Thread.currentThread().getName() + "更新成功？" + asr.compareAndSet(currentReference, "B", currentStamp, currentStamp + 1));
                printAtomicStampedReference(asr);
            }
        });
        ThreadPoolUtil.getInstance().submit(new Runnable() {
            @Override
            public void run() {
                int currentStamp = asr.getStamp();
                String currentReference = asr.getReference();
                System.out.println(Thread.currentThread().getName() + "更新成功？" + asr.compareAndSet(currentReference, "B", currentStamp, 0));
                printAtomicStampedReference(asr);
                System.out.println(Thread.currentThread().getName() + "更新成功？" + asr.compareAndSet(currentReference, "B", currentStamp, currentStamp + 1));
                printAtomicStampedReference(asr);
            }
        });
    }
}
