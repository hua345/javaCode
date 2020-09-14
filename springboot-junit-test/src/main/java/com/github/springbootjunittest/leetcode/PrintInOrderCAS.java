package com.github.springbootjunittest.leetcode;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenjianhua
 * @date 2020/9/14
 */
public class PrintInOrderCAS implements PrintInOrderInterface {
    private static AtomicInteger order = new AtomicInteger(0);

    public PrintInOrderCAS() {
    }

    @Override
    public void first(Runnable printFirst) throws InterruptedException {

        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        order.set(1);
    }

    @Override
    public void second(Runnable printSecond) throws InterruptedException {
        while (!order.compareAndSet(1, 1)) {
            Thread.yield();
        }
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        order.set(2);
    }

    @Override
    public void third(Runnable printThird) throws InterruptedException {
        while (!order.compareAndSet(2, 2)) {
            Thread.yield();
        }
        printThird.run();
        order.set(3);
    }
}
