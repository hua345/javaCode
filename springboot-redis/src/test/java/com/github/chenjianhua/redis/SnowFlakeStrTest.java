package com.github.chenjianhua.redis;

import com.github.chenjianhua.common.id.leaf.IdLeafRedisService;
import com.github.chenjianhua.common.id.util.SnowFlakeStr;
import com.github.chenjianhua.common.id.util.SnowFlakeStrUtil;
import com.github.chenjianhua.common.id.util.SnowFlakeUtil;
import com.github.common.util.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

/**
 * @author chenjianhua
 * @date 2020-09-07 15:41:49
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("dev")
public class SnowFlakeStrTest {

    private final static Integer num = 100000;

    private final static Integer threadNum = 12;

    @Test
    public void SnowFlakeStrTest() throws Exception {
        Set<String> tradeNumberSet = Collections.newSetFromMap(new ConcurrentHashMap<>());
        CountDownLatch latch = new CountDownLatch(threadNum);
        IntStream.range(0, threadNum).forEach(i -> {
            ThreadPoolUtil.getInstance().submit(() -> {
                for (int j = 0; j < num; j++) {
                    String tradeNumber = SnowFlakeStrUtil.getSnowFlakeStr("test");
                    if (tradeNumberSet.contains(tradeNumber)) {
                        log.info("重复tradeNumber:{}", tradeNumber);
                    } else {
                        tradeNumberSet.add(tradeNumber);
                    }
                }
                latch.countDown();
            });
        });
        latch.await();
        Assertions.assertEquals(threadNum * num, tradeNumberSet.size());
    }

    @Test
    public void SnowFlakeTest() throws Exception {
        Set<String> tradeNumberSet = Collections.newSetFromMap(new ConcurrentHashMap<>());
        CountDownLatch latch = new CountDownLatch(threadNum);
        IntStream.range(0, threadNum).forEach(i -> {
            ThreadPoolUtil.getInstance().submit(() -> {
                for (int j = 0; j < num; j++) {
                    String tradeNumber = SnowFlakeStrUtil.getSnowFlake("test");
                    if (tradeNumberSet.contains(tradeNumber)) {
                        log.info("重复tradeNumber:{}", tradeNumber);
                    } else {
                        tradeNumberSet.add(tradeNumber);
                    }
                }
                latch.countDown();
            });
        });
        latch.await();
        Assertions.assertEquals(threadNum * num, tradeNumberSet.size());
    }
}
