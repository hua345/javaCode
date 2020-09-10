package com.github.springbootjunittest;

import com.github.springbootjunittest.mythread.CyclicBarrierTask;
import com.github.springbootjunittest.mythread.SemaphoreTask;
import com.github.springbootjunittest.mythread.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * @author chenjianhua
 * @date 2020/9/10
 */
@Slf4j
public class CyclicBarrierTest {
    private static int threadCount = 20;

    @Test
    public void CyclicBarrierTest() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, new Runnable() {
            @Override
            public void run() {
                log.info("组队完成，开始游戏");
            }
        });
        for (int i = 0; i < threadCount; i++) {
            ThreadPoolUtil.getInstance().execute(new CyclicBarrierTask(cyclicBarrier, countDownLatch, i));
        }
        countDownLatch.await();
    }
}
