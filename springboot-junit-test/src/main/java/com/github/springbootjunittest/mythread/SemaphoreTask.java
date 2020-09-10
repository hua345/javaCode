package com.github.springbootjunittest.mythread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author chenjianhua
 * @date 2020/9/10
 */
@Slf4j
public class SemaphoreTask implements Runnable {
    private Semaphore semaphore;
    private CountDownLatch countDownLatch;

    private int value;

    public SemaphoreTask(Semaphore semaphore, CountDownLatch countDownLatch, int value) {
        this.semaphore = semaphore;
        this.countDownLatch = countDownLatch;
        this.value = value;
    }

    @Override
    public void run() {
        try {
            //获取资源
            semaphore.acquire();
            log.info("任务{} 占用一个资源...", value);
            TimeUnit.MILLISECONDS.sleep(500);
            log.info("任务{} 资源使用结束，释放资源", value);
            //释放资源
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            countDownLatch.countDown();
        }
    }
}
