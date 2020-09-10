package com.github.springbootjunittest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author chenjianhua
 * @date 2020/9/10
 */
@Slf4j
public class WaitNotifyTest {
    /**
     * wait()使当前线程阻塞，前提是 必须先获得锁，一般配合synchronized 关键字使用，
     * 即，一般在synchronized 同步代码块里使用 wait()、notify/notifyAll() 方法。
     */
    private static Object obj = new Object();

    static class WaitThread implements Runnable {
        @Override
        public void run() {
            synchronized (obj) {
                log.info("start wait!");
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("end wait!");
            }
        }
    }
    static class NotifyThread implements Runnable {
        @Override
        public void run() {
            synchronized (obj) {
                log.info("start notify!");
                obj.notify();
                log.info("end notify");
            }
        }
    }
    @Test
    public void WaitNotifyTest() {
        new Thread(new WaitThread()).start();
        new Thread(new NotifyThread()).start();
    }
}
