package com.github.chenjianhua.common.id.util;

import com.github.chenjianhua.common.json.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * @author chenjianhua
 * @date 2020-09-01 16:46:26
 */
@Slf4j
@Component
public class SnowFlakeUtil implements ApplicationRunner {
    private static volatile SnowFlake snowFlakeInstance = null;

    private static final String snowFlakeId = "snowFlakeId";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private SnowFlakeUtil() {
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Long machineId = redisTemplate.opsForValue().increment(snowFlakeId);
        if (machineId <= 0) {
            machineId = redisTemplate.opsForValue().increment(snowFlakeId);
        }
        machineId = machineId % SnowFlake.MAX_MACHINE_NUM;
        log.info("雪花算法机器ID：{}", machineId);
        snowFlakeInstance = new SnowFlake(1, machineId);
    }

    public static long getNextId() {
        if (null == snowFlakeInstance) {
            synchronized (SnowFlakeUtil.class) {
                if (null == snowFlakeInstance) {
                    log.info("雪花算法默认初始化");
                    snowFlakeInstance = new SnowFlake(2, 3);
                }
            }
        }
        return snowFlakeInstance.nextId();
    }

    public static void main(String[] args) {
        Date startDate = new Date(SnowFlake.START_TIME_STAMP);
        log.info(JsonUtil.toJsonString(startDate));
        LocalDateTime d2 = LocalDateTime.of(2020, 8, 15, 0, 0, 0);
        log.info("2020-08-15时间戳:{}", d2.toInstant(ZoneOffset.of("+8")).toEpochMilli());

        for (int i = 0; i < 10; i++) {
            log.info("id:{}", SnowFlakeUtil.getNextId());
        }
    }
}
