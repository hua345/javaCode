package com.github.springbootjunittest.leetcode;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenjianhua
 * @date 2020/9/14
 */
@Slf4j
public class PrintFooBarAlternatelySynchronized implements PrintFooBarAlternately1115 {
    private int n;

    private volatile boolean barStatus;
    private Object lock = new Object();

    public PrintFooBarAlternatelySynchronized(int n) {
        this.n = n;
    }

    @Override
    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            synchronized (lock) {
                if (barStatus) {
                    lock.wait();
                }
                // printFoo.run() outputs "foo". Do not change or remove this line.
                printFoo.run();
                barStatus = true;
                lock.notify();
            }
        }
    }

    @Override
    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            synchronized (lock) {
                if (!barStatus) {
                    lock.wait();
                }
                printBar.run();
                barStatus = false;
                lock.notify();
            }
        }
    }
}
