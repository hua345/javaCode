package com.github.springbootjunittest.designpattern.behavioralpattern.strategy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author chenjianhua
 * @date 2021/5/23
 */
@Getter
@AllArgsConstructor
public enum TestStrategyEnum implements BaseEnum<Integer> {

    ALGORITHM_A(1, "算法A"),
    ALGORITHM_B(2, "算法B");

    Integer type;
    String description;

    /**
     * @JsonValue: 在序列化时，只序列化 @JsonValue 注解标注的值,swagger也返回@JsonValue的内容
     * @JsonCreator: 在反序列化时，调用 @JsonCreator 标注的构造器或者工厂方法来创建对象
     */
    @JsonValue
    @Override
    public Integer getType() {
        return type;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static TestStrategyEnum findByType(Integer type) {
        for (TestStrategyEnum item : TestStrategyEnum.values()) {
            if (item.getType().equals(type)) {
                return item;
            }
        }
        return null;
    }
}