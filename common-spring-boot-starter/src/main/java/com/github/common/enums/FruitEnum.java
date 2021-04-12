package com.github.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
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
    /**
     * 水果,测试的枚举
     */
    Apple(1, "苹果"),
    banana(2, "香蕉");

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
    public static FruitEnum findByType(Integer type) {
        for (FruitEnum item : FruitEnum.values()) {
            if (item.getType().equals(type)) {
                return item;
            }
        }
        return null;
    }
}
