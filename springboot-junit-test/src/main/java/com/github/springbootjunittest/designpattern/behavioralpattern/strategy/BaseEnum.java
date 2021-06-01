package com.github.springbootjunittest.designpattern.behavioralpattern.strategy;

/**
 * @author chenjianhua
 * @date 2020-09-01 16:46:26
 */
public interface BaseEnum<T> {
    /**
     * 获取枚举的类型
     *
     * @return 枚举的类型
     */
    T getType();

    /**
     * 通过类型查询枚举
     *
     * @param type 枚举的类型
     * @param <T>  枚举的类型
     * @return 枚举值
     */
    static <T> BaseEnum findByType(T type) {
        return null;
    }
}

