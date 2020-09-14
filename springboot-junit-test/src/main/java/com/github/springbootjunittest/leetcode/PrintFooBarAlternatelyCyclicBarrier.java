package com.github.springbootjunittest.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * @author chenjianhua
 * @date 2020/9/14
 */
@Slf4j
public class PrintFooBarAlternatelyCyclicBarrier implements PrintFooBarAlternately1115 {
    private int n;

    private CyclicBarrier cyclicBarrier;
    private CountDownLatch countDownLatch;

    public PrintFooBarAlternatelyCyclicBarrier(int n) {
        this.n = n;
        this.cyclicBarrier = new CyclicBarrier(2, new Runnable() {
            @Override
            public void run() {
                log.info("组队完成，开始游戏");
            }
        });
        this.countDownLatch = new CountDownLatch(1);
    }

    @Override
    public void foo(Runnable printFoo) throws InterruptedException {
        try {
            for (int i = 0; i < n; i++) {
                // printFoo.run() outputs "foo". Do not change or remove this line.
                printFoo.run();
                countDownLatch.countDown();
                cyclicBarrier.await();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void bar(Runnable printBar) throws InterruptedException {
        try {
            for (int i = 0; i < n; i++) {
                countDownLatch.await();
                // printBar.run() outputs "bar". Do not change or remove this line.
                printBar.run();
                countDownLatch = new CountDownLatch(1);
                cyclicBarrier.await();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
