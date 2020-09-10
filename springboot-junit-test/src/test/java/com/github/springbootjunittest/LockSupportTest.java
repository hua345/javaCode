package com.github.springbootjunittest;

import com.github.springbootjunittest.mythread.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author chenjianhua
 * @date 2020/9/10
 */
@Slf4j
public class LockSupportTest {
    /**
     * LockSupport类，是JUC包中的一个工具类，是用来创建锁和其他同步类的基本线程阻塞
     * LockSupport类的核心方法其实就两个：park()和unpark()，其中park()方法用来阻塞当前调用线程，unpark()方法用于唤醒指定线程。
     * LockSuport主要是针对Thread进进行阻塞处理，可以指定阻塞队列的目标对象，每次可以指定具体的线程唤醒。
     * Object.wait()是以对象为维度，阻塞当前的线程和唤醒单个(随机)或者所有线程。
     */
    @Test
    public void parkAndUnparkTest() throws Exception {
        String a = "A";
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("睡觉");
                LockSupport.park(a);
                log.info("起床");
            }
        }, "LockSupport-Thread");
        t.start();
        TimeUnit.MILLISECONDS.sleep(1000);
        log.info("老婆喊我起床");
        LockSupport.unpark(t);
    }

    @Test
    public void unpackAndPack() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        String a = "A";
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                log.info("睡觉");
                LockSupport.park(a);
                log.info("起床");
                countDownLatch.countDown();
            }
        }, "LockSupport-Thread");
        t.start();
        log.info("约老婆明天去玩");
        LockSupport.unpark(t);
        countDownLatch.await();
    }
}
