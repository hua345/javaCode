package com.github.springbootjunittest.mythread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author chenjianhua
 * @date 2020/9/10
 */
@Slf4j
public class CyclicBarrierTask implements Runnable {
    private CyclicBarrier cyclicBarrier;
    private CountDownLatch countDownLatch;

    private int value;

    public CyclicBarrierTask(CyclicBarrier cyclicBarrier, CountDownLatch countDownLatch, int value) {
        this.cyclicBarrier = cyclicBarrier;
        this.countDownLatch = countDownLatch;
        this.value = value;
    }

    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(500);
            log.info("玩家{}已经准备,等待其他玩家准备...", value);
            cyclicBarrier.await();
            log.info("玩家{}开始游戏", value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            countDownLatch.countDown();
        }
    }
}