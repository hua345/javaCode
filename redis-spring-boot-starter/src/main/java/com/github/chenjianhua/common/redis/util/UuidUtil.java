package com.github.chenjianhua.common.redis.util;

import java.util.UUID;

/**
 * @author chenjianhua
 * @date 2020/12/07
 */
public class UuidUtil {
    /**
     * 获取UUID
     *
     * @return
     */
    public static String getUuid32() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    public static String getUuid8() {
        return getUuid32().substring(0, 8);
    }
}
