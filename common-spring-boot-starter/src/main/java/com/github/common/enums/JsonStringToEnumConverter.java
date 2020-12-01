package com.github.common.enums;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chenjianhua
 * @date 2020-09-01 16:46:26
 */
@Slf4j
public class JsonStringToEnumConverter implements Converter<String, BaseEnum> {

    @Override
    public BaseEnum convert(String type) {
        return BaseEnum.findByType(type);
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return typeFactory.constructType(String.class);
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return typeFactory.constructType(BaseEnum.class);
    }
}
