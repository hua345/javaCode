package com.github.springbootjunittest.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author chenjianhua
 * @date 2020/9/14
 */
@Slf4j
public class PrintFooBarAlternatelyBlockingQueue implements PrintFooBarAlternately1115 {
    private int n;

    private BlockingQueue<Integer> barQueue;
    private BlockingQueue<Integer> fooQueue;

    public PrintFooBarAlternatelyBlockingQueue(int n) {
        this.n = n;
        barQueue = new LinkedBlockingQueue<>(1);
        fooQueue = new LinkedBlockingQueue<>(1);
        barQueue.add(1);
    }

    @Override
    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            barQueue.take();
            // printFoo.run() outputs "foo". Do not change or remove this line.
            printFoo.run();
            fooQueue.add(1);
        }
    }

    @Override
    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            fooQueue.take();
            printBar.run();
            barQueue.add(1);
        }
    }
}
