package com.github.springbootjunittest;

import com.github.springbootjunittest.mythread.ThreadPoolUtil;
import com.github.springbootjunittest.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author chenjianhua
 * @date 2020/9/11
 */
@Slf4j
public class BlockingQueueTest {
    private static int producerThreadCount = 2;
    private static int consumerThreadCount = 6;
    private static int productNum = 100;
    private static String Apple = "苹果";

    @Test
    public void BlockingQueueTest() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(producerThreadCount + consumerThreadCount);
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10);
        for (int i = 0; i < producerThreadCount; i++) {
            ThreadPoolUtil.getInstance().submit(new Runnable() {
                @Override
                public void run() {
                    int threadProductNum = (consumerThreadCount * productNum) / producerThreadCount;
                    try {
                        for (int j = 0; j < threadProductNum; j++) {
                            String product = Apple + DateUtil.getCurrentDate();
                            log.info("生产:{}", product);
                            queue.put(product);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        countDownLatch.countDown();
                    }
                }
            });
        }
        for (int i = 0; i < consumerThreadCount; i++) {
            ThreadPoolUtil.getInstance().submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int j = 0; j < productNum; j++) {
                            log.info("prepare 消费");
                            TimeUnit.MILLISECONDS.sleep(1000);
                            log.info("消费：{}", queue.take());
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        countDownLatch.countDown();
                    }
                }
            });
        }
        countDownLatch.await();
    }
}
