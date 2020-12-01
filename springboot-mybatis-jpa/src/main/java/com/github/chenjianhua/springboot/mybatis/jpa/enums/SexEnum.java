package com.github.chenjianhua.springboot.mybatis.jpa.enums;

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
    Male(1, "男"),
    Female(2, "女");

    /**
     * 一个类只能用一个,当加上@JsonValue注解时，序列化是只返回这一个字段的值,swagger也返回@JsonValue的内容
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