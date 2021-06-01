package com.github.chenjianhua.redis;

import com.github.chenjianhua.common.id.leaf.IdLeafRedisService;
import com.github.chenjianhua.common.id.util.SnowFlake;
import com.github.chenjianhua.common.id.util.SnowFlakeStrUtil;
import com.github.chenjianhua.common.id.util.SnowFlakeUtil;
import com.github.common.util.DateFormatEnum;
import com.github.common.util.DateUtil;
import com.github.common.util.ThreadPoolUtil;
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
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("dev")
public class SnowFlakeTest {

    private static final Logger log = LoggerFactory.getLogger(SnowFlakeTest.class);

    private final static Integer num = 100000;

    private final static Integer threadNum = 12;

    @Test
    public void SnowFlakeTest() throws Exception {
        Set<Long> tradeNumberSet = Collections.newSetFromMap(new ConcurrentHashMap<>());
        CountDownLatch latch = new CountDownLatch(threadNum);
        Long beginId = SnowFlakeUtil.getNextId();
        IntStream.range(0, threadNum).forEach(i -> {
            ThreadPoolUtil.getInstance().submit(() -> {
                Long lastId = beginId;
                for (int j = 0; j < num; j++) {
                    Long currentId = SnowFlakeUtil.getNextId();
                    Assertions.assertTrue(currentId > lastId);
                    lastId = currentId;
                    if (tradeNumberSet.contains(currentId)) {
                        log.info("重复id:{}", currentId);
                    } else {
                        tradeNumberSet.add(currentId);
                    }
                }
                latch.countDown();
            });
        });
        latch.await();
        Assertions.assertEquals(threadNum * num, tradeNumberSet.size());
    }
}
