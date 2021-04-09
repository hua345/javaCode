package com.github.chenjianhua.common.encrypt.util;

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
    public static String getUUID32() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    public static String getUUID8() {
        return getUUID32().substring(0, 8);
    }
}
