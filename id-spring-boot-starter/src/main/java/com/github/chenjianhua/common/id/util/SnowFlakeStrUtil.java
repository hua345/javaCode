package com.github.chenjianhua.common.id.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenjianhua
 * @date 2021/6/1
 * 前缀 + 14位年月日时分秒长度 + 8位雪花算法数字
 */
@Slf4j
public class SnowFlakeStrUtil {

    public final static Map<String, SnowFlakeStr> SNOW_FLAKE_STR_MAP = new HashMap<>();

    private final static String DEFAULT_PREFIX = "default";

    public static String getSnowFlake(String prefix) {
        StringBuilder sb = new StringBuilder(prefix);
        sb.append(LocalDateTime.now().format(SnowFlakeStr.DATE_TIME_FORMATTER));
        String nextIdStr = String.valueOf(SnowFlakeUtil.getNextId());
        sb.append(nextIdStr.substring(nextIdStr.length() - 8));
        return sb.toString();
    }

    public static String getSnowFlakeStr(String prefix) {
        if (!StringUtils.hasText(prefix)) {
            prefix = DEFAULT_PREFIX;
        }
        if (!SNOW_FLAKE_STR_MAP.containsKey(prefix)) {
            synchronized (SNOW_FLAKE_STR_MAP) {
                if (!SNOW_FLAKE_STR_MAP.containsKey(prefix)) {
                    SNOW_FLAKE_STR_MAP.put(prefix, new SnowFlakeStr(prefix, SnowFlakeUtil.getMachineId()));
                }
            }
        }
        return SNOW_FLAKE_STR_MAP.get(prefix).getOrderNumber();
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            log.info("orderNumber:{}", SnowFlakeStrUtil.getSnowFlakeStr("test"));
        }
        log.info("hello");
    }
}
