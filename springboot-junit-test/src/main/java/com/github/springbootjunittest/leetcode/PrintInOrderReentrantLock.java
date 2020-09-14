package com.github.springbootjunittest.leetcode;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chenjianhua
 * @date 2020/9/14
 */
public class PrintInOrderReentrantLock implements PrintInOrderInterface {
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private int count = 1;

    public PrintInOrderReentrantLock() {
    }

    @Override
    public void first(Runnable printFirst) throws InterruptedException {
        lock.lock();
        try {
            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            count = 2;
            //其实这里调用signal是有问题
            //假设这里唤醒第三个线程
            //第三个线程会再次调用await,进入等待池等待唤醒
            //第二个线程此时也在等待池中
            //这样就会导致第一个线程和第二个线程都在等待池中
            //condition.signal();
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void second(Runnable printSecond) throws InterruptedException {
        lock.lock();
        try {
            //为什么这里可以使用if,而下面必须使用while呢
            //如果线程一运行完,线程三拿到了锁,则需要再次判断count值让其进入等待池
            if (count != 2) {
                condition.await();
            }
            // printSecond.run() outputs "second". Do not change or remove this line.
            printSecond.run();
            count = 3;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void third(Runnable printThird) throws InterruptedException {
        lock.lock();
        try {
            while (count != 3) {
                condition.await();
            }
            // printThird.run() outputs "third". Do not change or remove this line.
            printThird.run();
        } finally {
            lock.unlock();
        }
    }
}
