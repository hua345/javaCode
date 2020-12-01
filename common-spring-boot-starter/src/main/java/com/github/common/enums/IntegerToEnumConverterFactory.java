package com.github.common.enums;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.Objects;

/**
 * @author chenjianhua
 * @date 2020-09-01 16:46:26
 */
public class IntegerToEnumConverterFactory implements ConverterFactory<Integer, BaseEnum> {

    @Override
    public <T extends BaseEnum> Converter<Integer, T> getConverter(Class<T> clazz) {
        return new IntegerToEnum<>(clazz);
    }

    private static class IntegerToEnum<T extends BaseEnum> implements Converter<Integer, T> {

        private final T[] values;

        IntegerToEnum(Class<T> clazz) {
            values = clazz.getEnumConstants();
        }

        @Override
        public T convert(Integer type) {
            if (Objects.isNull(type)) {
                return null;
            }
            for (T t : values) {
                if (t.getType().equals(type)) { //-128~127可以自动拆箱
                    return t;
                }
            }
            return null;
        }
    }
}

