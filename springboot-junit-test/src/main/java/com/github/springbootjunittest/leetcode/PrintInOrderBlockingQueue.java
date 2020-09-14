package com.github.springbootjunittest.leetcode;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author chenjianhua
 * @date 2020/9/14
 */
public class PrintInOrderBlockingQueue implements PrintInOrderInterface {
    private BlockingQueue<Integer> one;
    private BlockingQueue<Integer> two;

    public PrintInOrderBlockingQueue() {
        this.one = new LinkedBlockingQueue<>();
        this.two = new LinkedBlockingQueue<>();
    }

    @Override
    public void first(Runnable printFirst) throws InterruptedException {

        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        one.add(1);
    }

    @Override
    public void second(Runnable printSecond) throws InterruptedException {

        // printSecond.run() outputs "second". Do not change or remove this line.
        one.take();
        printSecond.run();
        two.add(2);
    }

    @Override
    public void third(Runnable printThird) throws InterruptedException {

        // printThird.run() outputs "third". Do not change or remove this line.
        two.take();
        printThird.run();
    }
}
