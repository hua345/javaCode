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
public class PrintInOrderTest {

    private final static String FIRST = "first";
    private final static String SECOND = "second";
    private final static String THIRD = "third";

    public void test(PrintInOrderInterface printInOrder) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        BlockingQueue<String> blockingQueue = new LinkedBlockingQueue(4);
        ThreadPoolUtil.getInstance().submit(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                printInOrder.third(new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        TimeUnit.MILLISECONDS.sleep(500);
                        blockingQueue.add(THIRD);
                        countDownLatch.countDown();
                    }
                });
            }
        });
        ThreadPoolUtil.getInstance().submit(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                printInOrder.first(new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        TimeUnit.MILLISECONDS.sleep(500);
                        blockingQueue.add(FIRST);
                        countDownLatch.countDown();
                    }
                });
            }
        });
        ThreadPoolUtil.getInstance().submit(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                printInOrder.second(new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        TimeUnit.MILLISECONDS.sleep(500);
                        blockingQueue.add(SECOND);
                        countDownLatch.countDown();
                    }
                });
            }
        });
        countDownLatch.await();
        String value = blockingQueue.take();
        assertEquals(FIRST, value);
        value = blockingQueue.take();
        assertEquals(SECOND, value);
        value = blockingQueue.take();
        assertEquals(THIRD, value);
    }

    @Test
    public void PrintInOrderBlockingQueueTest() throws Exception {
        PrintInOrderBlockingQueue printInOrderBlockingQueue = new PrintInOrderBlockingQueue();
        test(printInOrderBlockingQueue);
    }

    @Test
    public void PrintInOrderSemaphoreTest() throws Exception {
        PrintInOrderSemaphore printInOrderSemaphore = new PrintInOrderSemaphore();
        test(printInOrderSemaphore);
    }

    @Test
    public void PrintInOrderSyncTest() throws Exception {
        PrintInOrderSync printInOrderSync = new PrintInOrderSync();
        test(printInOrderSync);
    }

    @Test
    public void PrintInOrderReentrantLockTest() throws Exception {
        PrintInOrderReentrantLock printInOrderReentrantLock = new PrintInOrderReentrantLock();
        test(printInOrderReentrantLock);
    }

    @Test
    public void PrintInOrderCASTest() throws Exception {
        PrintInOrderCAS printInOrderCAS = new PrintInOrderCAS();
        test(printInOrderCAS);
    }
}
