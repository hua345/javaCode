package com.github.springbootjunittest.mythread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author chenjianhua
 * @date 2020/9/11
 */
@Slf4j
public class Consumer {
    private final BlockingQueue<String> queue;

    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            while (true) {
                log.info("prepare 消费");
                TimeUnit.MILLISECONDS.sleep(1000);
                log.info("starting：{}", queue.take());
                log.info("end 消费");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
