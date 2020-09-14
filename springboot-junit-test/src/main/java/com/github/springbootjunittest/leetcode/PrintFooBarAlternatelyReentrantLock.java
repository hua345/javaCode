package com.github.springbootjunittest.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chenjianhua
 * @date 2020/9/14
 */
@Slf4j
public class PrintFooBarAlternatelyReentrantLock implements PrintFooBarAlternately1115 {
    private int n;

    private ReentrantLock lock = new ReentrantLock();
    private Condition fooCondition = lock.newCondition();
    private Condition barCondition = lock.newCondition();

    private volatile int count = 1;

    public PrintFooBarAlternatelyReentrantLock(int n) {
        this.n = n;
    }

    @Override
    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            lock.lock();
            if (count != 1) {
                fooCondition.await();
            }
            // printFoo.run() outputs "foo". Do not change or remove this line.
            printFoo.run();
            count = 2;
            barCondition.signal();
            lock.unlock();
        }
    }

    @Override
    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            lock.lock();
            if (count != 2) {
                barCondition.await();
            }
            // printBar.run() outputs "bar". Do not change or remove this line.
            printBar.run();
            count = 1;
            fooCondition.signal();
            lock.unlock();
        }
    }
}
