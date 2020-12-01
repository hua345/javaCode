package com.github.common.enums;

/**
 * @author chenjianhua
 * @date 2020-09-01 16:46:26
 */
public interface BaseEnum<T> {
    /**
     * 索引或类型
     */
    T getType();

    static <T> BaseEnum findByType(T type) {
        return null;
    }
}

