package com.github.chenjianhua.springbootelasticsearch;

import com.github.chenjianhua.springbootelasticsearch.util.OkHttpUtil;
import com.github.chenjianhua.springbootelasticsearch.util.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;


@Slf4j
@SpringBootTest
public class HttpTest {
    private final static String baiduUrl = "https://www.baidu.com";

    private final static Integer threadNum = 100;

    @Test
    public void testOkHttp() throws Exception {
        Response response = OkHttpUtil.getSync(baiduUrl);
        Assert.isTrue(response.isSuccessful());
        log.info(response.body().string());
        response.close();
    }

    @Test
    public void testOkHttp2() throws Exception {
        for (int i = 0; i < threadNum; i++) {
            Response response = OkHttpUtil.getSync(baiduUrl);
            Assert.isTrue(response.isSuccessful());
            response.close();
        }
    }

    @Test
    public void testOkHttpThreadPool() throws Exception {
        CountDownLatch latch = new CountDownLatch(threadNum);
        IntStream.range(0, threadNum).forEach(i -> {
            ThreadPoolUtil.getInstance().submit(() -> {
                Response response = null;
                try {
                    response = OkHttpUtil.getSync(baiduUrl);
                    Assert.isTrue(response.isSuccessful());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    response.close();
                    latch.countDown();
                }
            });
        });
        latch.await();
    }
}
