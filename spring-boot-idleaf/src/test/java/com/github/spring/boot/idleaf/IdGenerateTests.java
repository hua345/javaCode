package com.github.spring.boot.idleaf;


import com.github.common.util.DateFormatEnum;
import com.github.common.util.DateUtil;
import com.github.common.util.ThreadPoolUtil;
import com.github.id.leaf.IdLeafRedisService;
import com.github.id.util.SnowFlake;
import com.github.id.util.SnowFlakeUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * @author chenjianhua
 * @date 2020-09-01 16:46:26
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class IdGenerateTests {

    private static final Logger log = LoggerFactory.getLogger(IdGenerateTests.class);

    @Autowired
    private IdLeafRedisService redisLeaf;

    private final static Integer num = 2000;

    private final static Integer threadNum = 10;

    @Test
    public void SnowFlakeTest() {
        log.info("雪花算法起始时间:{}", DateUtil.formatDateTime(SnowFlake.START_STMP, DateFormatEnum.DATE_YYYY_MM_DD_HH_MM_SS));
        Long startMS = System.currentTimeMillis();
        for (int i = 0; i < num * 2; i++) {
            SnowFlakeUtil.getNextId();
        }
        Long endMS = System.currentTimeMillis();
        log.info("雪花算法生成100万id耗时：{}ms", endMS - startMS);
    }

    @Test
    public void testRedisLeaf() throws Exception {
        CountDownLatch latch = new CountDownLatch(threadNum);
        Long beginId = redisLeaf.getIdByBizTag("leaf-segment-test");
        IntStream.range(0, threadNum).forEach(i -> {
            ThreadPoolUtil.getInstance().submit(() -> {
                Long id = beginId;
                for (int j = 0; j < num; j++) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Long currentId = redisLeaf.getIdByBizTag("leaf-segment-test");
                    Assert.assertTrue(currentId > id);
                    id = currentId;
                }
                latch.countDown();
            });
        });
        latch.await();
        Long endId = redisLeaf.getIdByBizTag("leaf-segment-test");
        Long expectedId = beginId + num * threadNum + 1;
        Assert.assertEquals(expectedId, endId);
    }

}
