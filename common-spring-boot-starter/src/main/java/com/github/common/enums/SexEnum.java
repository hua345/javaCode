package com.github.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenjianhua
 * @date 2020/6/4
 */
@Getter
public enum SexEnum implements BaseEnum<Integer> {
    /**
     * 性别,测试的枚举
     */
    Male(1, "男"),
    Female(2, "女");

    /**
     * @JsonValue: 在序列化时，只序列化 @JsonValue 注解标注的值,swagger也返回@JsonValue的内容
     * @JsonCreator: 在反序列化时，调用 @JsonCreator 标注的构造器或者工厂方法来创建对象
     */
    @JsonValue
    public Map<String, Object> getEnumValue() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("desc", description);
        return map;
    }

    Integer type;
    String description;

    SexEnum(int type, String description) {
        this.type = type;
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static SexEnum findByType(Integer type) {
        SexEnum[] arr = SexEnum.values();
        for (SexEnum item : arr) {
            if (item.getType().equals(type)) {
                return item;
            }
        }
        return null;
    }
}