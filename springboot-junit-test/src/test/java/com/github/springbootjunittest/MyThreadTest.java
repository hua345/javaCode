package com.github.springbootjunittest;

import com.github.springbootjunittest.mythread.MyCallable;
import com.github.springbootjunittest.mythread.ThreadPoolUtil;
import com.github.springbootjunittest.util.DateUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenjianhua
 * @date 2020/9/9
 */
public class MyThreadTest {

    @Test
    public void myThreadTest() {
        MyCallable myCallable = new MyCallable();
        Future<String> future = ThreadPoolUtil.getInstance().submit(myCallable);
        try {
            System.out.println("result: " + future.get());
            System.out.println(Thread.currentThread().getName() + " End. Time = " + DateUtil.getCurrentDate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        System.out.println(count.get());
    }
}
