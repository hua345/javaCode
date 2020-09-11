package com.github.springbootjunittest.mythread;

import com.github.springbootjunittest.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author chenjianhua
 * @date 2020/9/11
 */
@Slf4j
public class Producer {
    private final BlockingQueue<String> fileQueue;

    public Producer(BlockingQueue<String> queue) {
        this.fileQueue = queue;

    }

    public void run() {
        try {
            while (true) {
                TimeUnit.MILLISECONDS.sleep(1000);
                String product = this.produce();
                log.info("生产:{}", product);
                fileQueue.put(product);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public String produce() {
        return DateUtil.getCurrentDate();
    }
}
