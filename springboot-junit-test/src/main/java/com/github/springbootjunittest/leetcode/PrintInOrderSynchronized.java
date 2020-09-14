package com.github.springbootjunittest.leetcode;

/**
 * @author chenjianhua
 * @date 2020/9/14
 */
public class PrintInOrderSynchronized implements PrintInOrderInterface1114 {
    private boolean firstFinished;
    private boolean secondFinished;
    private Object lock = new Object();

    public PrintInOrderSynchronized() {
    }

    @Override
    public void first(Runnable printFirst) throws InterruptedException {
        synchronized (lock) {
            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            firstFinished = true;
            lock.notifyAll();
        }

    }

    @Override
    public void second(Runnable printSecond) throws InterruptedException {
        synchronized (lock) {
            while (!firstFinished) {
                lock.wait();
            }

            // printSecond.run() outputs "second". Do not change or remove this line.
            printSecond.run();
            secondFinished = true;
            lock.notifyAll();
        }
    }

    @Override
    public void third(Runnable printThird) throws InterruptedException {
        synchronized (lock) {
            while (!secondFinished) {
                lock.wait();
            }

            // printSecond.run() outputs "second". Do not change or remove this line.
            printThird.run();
        }
    }
}
