package com.github.springbootjunittest.java;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author chenjianhua
 * @date 2021/6/26
 */
public class ThreadSleepTest {
    @Test
    public void threadSleepTest() throws InterruptedException {
        Thread.sleep(2000);
    }

    @Test
    public void TimeUnitSleepTest() throws InterruptedException {
        TimeUnit.SECONDS.sleep(4);
    }
}
