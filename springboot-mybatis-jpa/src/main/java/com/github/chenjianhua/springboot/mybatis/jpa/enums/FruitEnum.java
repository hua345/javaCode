package com.github.chenjianhua.springboot.mybatis.jpa.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author chenjianhua
 * @date 2020/11/16
 */
@Getter
@AllArgsConstructor
public enum FruitEnum implements BaseEnum<Integer> {
    Apple(1, "苹果"),
    banana(2, "香蕉");

    Integer type;
    String description;

    /**
     * 一个类只能用一个,当加上@JsonValue注解时，序列化是只返回这一个字段的值,swagger也返回@JsonValue的内容
     */
    @JsonValue
    @Override
    public Integer getType() {
        return type;
    }

    public static FruitEnum findByType(Integer type) {
        for (FruitEnum item : FruitEnum.values()) {
            if (item.getType().equals(type)) {
                return item;
            }
        }
        return null;
    }
}
