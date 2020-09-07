package com.github.chenjianhua.springboot.mybatis.jpa.utils;

import java.util.UUID;

/**
 * @author chenjianhua
 * @date 2020-09-07 15:41:49
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
