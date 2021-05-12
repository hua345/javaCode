package com.github.springbootjunittest.thread;

import com.github.springbootjunittest.mythread.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * @author chenjianhua
 * @date 2020/10/29
 */
@Slf4j
public class FutureAndCompletableFutureTest {

    @Test
    public void test() throws ExecutionException, InterruptedException {
        // Future就是对于具体的Runnable或者Callable任务的执行结果进行取消、查询是否完成、获取结果。
        FutureTask<Integer> future1 = new FutureTask<Integer>(() -> {
            TimeUnit.MILLISECONDS.sleep(1000);
            return 100;
        });
        ThreadPoolUtil.getInstance().submit(future1);
        log.info("任务结果:{}", future1.get());
        Future<Integer> future2 = ThreadPoolUtil.getInstance().submit(() -> {
            TimeUnit.MILLISECONDS.sleep(1000);
            return 100;
        });
        log.info("任务结果:{}", future2.get());
    }

    @Test
    public void testCompletableFuture() throws InterruptedException, ExecutionException {
        // CompletableFuture默认的线程池ForkJoinPool.commonPool
        // ForkJoinPool 最适合的是计算密集型的任务,主要用于实现“分而治之”的算法，特别是分治之后递归调用的函数，例如 quick sort 等。
        // runAsync方法不支持返回值。
        // supplyAsync可以支持返回值。
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        });
        CompletableFuture<String> future2 = future1.thenCompose(i -> CompletableFuture.supplyAsync(() -> String.valueOf(i * 10)));
        log.info("任务结果:{}", future2.get());
    }
}
