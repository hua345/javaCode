package com.github.common.enums;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;

/**
 * @author chenjianhua
 * @date 2020-09-01 16:46:26
 */
public class JsonIntegerToEnumConverter implements Converter<Integer, BaseEnum> {

    @Override
    public BaseEnum convert(Integer type) {
        return BaseEnum.findByType(type);
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return typeFactory.constructType(Integer.class);
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return typeFactory.constructType(BaseEnum.class);
    }
}
