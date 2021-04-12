package com.github.chenjianhua.common.id.util;

import java.util.UUID;

/**
 * @author chenjianhua
 * @date 2020-09-01 16:46:26
 */
public class UUIDUtil {
    /**
     * 获取UUID
     * @return
     */
    public static String getUUID32() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
}
