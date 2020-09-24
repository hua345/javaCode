package com.github.spring.boot.idleaf;

import com.github.spring.boot.idleaf.service.idleaf.IdLeafMysqlService;
import com.github.spring.boot.idleaf.service.idleaf.IdLeafRedisService;
import com.github.spring.boot.idleaf.utils.*;
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
    private IdLeafMysqlService leaf;

    @Autowired
    private IdLeafRedisService redisLeaf;

    private final static Integer num = 100000;

    @Test
    public void SnowFlakeTest() {
        log.info("雪花算法起始时间:{}", DateUtil.formatDateTime(SnowFlake.START_STMP, DateFormatEnum.DATE_YYYY_MM_DD_HH_MM_SS));
        Long startMS = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            SnowFlakeUtil.getNextId();
        }
        Long endMS = System.currentTimeMillis();
        log.info("雪花算法生成100万id耗时：{}ms", endMS - startMS);
    }

    @Test
    public void testMysqlLeaf() throws Exception {
        CountDownLatch latch = new CountDownLatch(2);
        Long beginId = leaf.getIdByBizTag("leaf-segment-test");
        ThreadPoolUtil.getInstance().submit(() -> {
            Long id = 0L;
            for (int i = 0; i < 100000; i++) {
                Long currentId = leaf.getIdByBizTag("leaf-segment-test");
                Assert.assertTrue(currentId > id);
                id = currentId;
            }
            latch.countDown();
        });
        ThreadPoolUtil.getInstance().submit(() -> {
            Long id = 0L;
            for (int i = 0; i < 100000; i++) {
                Long currentId = leaf.getIdByBizTag("leaf-segment-test");
                Assert.assertTrue(currentId > id);
                id = currentId;
            }
            latch.countDown();
        });
        latch.await();
        Long endId = leaf.getIdByBizTag("leaf-segment-test");
        Long expectedId = beginId + num * 2 + 1;
        Assert.assertEquals(expectedId, endId);
    }

    @Test
    public void testRedisLeaf() throws Exception {
        CountDownLatch latch = new CountDownLatch(2);
        Long beginId = redisLeaf.getIdByBizTag("leaf-segment-test");
        ThreadPoolUtil.getInstance().submit(() -> {
            Long id = beginId;
            for (int i = 0; i < num; i++) {
                Long currentId = redisLeaf.getIdByBizTag("leaf-segment-test");
                Assert.assertTrue(currentId > id);
                id = currentId;
            }
            latch.countDown();
        });
        ThreadPoolUtil.getInstance().submit(() -> {
            Long id = beginId;
            for (int i = 0; i < num; i++) {
                Long currentId = redisLeaf.getIdByBizTag("leaf-segment-test");
                Assert.assertTrue(currentId > id);
                id = currentId;
            }
            latch.countDown();
        });
        latch.await();
        Long endId = redisLeaf.getIdByBizTag("leaf-segment-test");
        Long expectedId = beginId + num * 2 + 1;
        Assert.assertEquals(expectedId, endId);
    }
}
