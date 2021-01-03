package com.github.chenjianhua.springbootexcel.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.github.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author chenjianhua
 * @date 2020/12/23
 */
@Getter
@AllArgsConstructor
public enum MyExcelTypeEnum implements BaseEnum<String> {

    TEST_EXPORT("TEST_EXPORT", 0, "测试导出"),
    TEST_CURRENT_EXPORT("TEST_CURRENT_EXPORT", 0, "异步测试导出");



    String type;
    int excelType;
    String description;

    /**
     * @JsonValue: 在序列化时，只序列化 @JsonValue 注解标注的值,swagger也返回@JsonValue的内容
     * @JsonCreator: 在反序列化时，调用 @JsonCreator 标注的构造器或者工厂方法来创建对象
     */
    @JsonValue
    @Override
    public String getType() {
        return type;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static MyExcelTypeEnum findByType(String type) {
        for (MyExcelTypeEnum item : MyExcelTypeEnum.values()) {
            if (item.getType().equals(type)) {
                return item;
            }
        }
        return null;
    }
}
