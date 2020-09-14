package com.github.springbootjunittest.leetcode;

import java.util.concurrent.Semaphore;

/**
 * @author chenjianhua
 * @date 2020/9/14
 */
public class PrintInOrderSemaphore implements PrintInOrderInterface1114 {
    private Semaphore s2;
    private Semaphore s3;

    public PrintInOrderSemaphore() {
        s2 = new Semaphore(0);
        s3 = new Semaphore(0);
    }

    @Override
    public void first(Runnable printFirst) throws InterruptedException {

        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        s2.release();
    }

    @Override
    public void second(Runnable printSecond) throws InterruptedException {

        // printSecond.run() outputs "second". Do not change or remove this line.
        s2.acquire();
        printSecond.run();
        s3.release();
    }

    @Override
    public void third(Runnable printThird) throws InterruptedException {

        // printThird.run() outputs "third". Do not change or remove this line.
        s3.acquire();
        printThird.run();
    }
}
