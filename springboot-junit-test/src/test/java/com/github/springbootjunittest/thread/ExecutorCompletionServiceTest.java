package com.github.springbootjunittest.thread;

import com.github.springbootjunittest.mythread.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;

/**
 * @author chenjianhua
 * @date 2020/12/22
 */
@Slf4j
public class ExecutorCompletionServiceTest {
    private static int threadCount = 20;

    /**
     * ExecutorCompletionService
     * 内部有一个先进先出的阻塞队列，用于保存已经执行完成的Future，通过调用它的take方法或poll方法可以获取到一个已经执行完成的Future，
     * 进而通过调用Future接口实现类的get方法获取最终的结果。
     * Future<V> take():从内部阻塞队列中获取并移除第一个执行完成的任务，阻塞，直到有任务完成；
     * Future<V> poll():从内部阻塞队列中获取并移除第一个执行完成的任务，获取不到则返回null，不阻塞；
     * @throws Exception
     */
    @Test
    public void TestExecutorCompletionService() throws Exception {
        CompletionService<String> service = new ExecutorCompletionService<>(ThreadPoolUtil.getInstance());
        for (int i = 0; i < 5; i++) {
            int seqNo = i;
            service.submit(() -> "HelloWorld-" + seqNo + "-" + Thread.currentThread().getName());
        }
        for (int j = 0; j < 6; j++) {
            Future<String> future = service.poll();
            if(null != future){
                log.info(future.get());
            }
        }
    }
}
