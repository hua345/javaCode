package com.github.common.enums;

import com.github.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.util.StringUtils;

/**
 * @author chenjianhua
 * @date 2020-09-01 16:46:26
 */
@Slf4j
public class StringToEnumConverterFactory implements ConverterFactory<String, BaseEnum> {

    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> clazz) {
        return new StringToEnum<>(clazz);
    }

    private static class StringToEnum<T extends BaseEnum> implements Converter<String, T> {

        private final T[] values;

        StringToEnum(Class<T> clazz) {
            values = clazz.getEnumConstants();
        }

        @Override
        public T convert(String type) {
            if(StringUtils.isEmpty(type)){
                return null;
            }
            log.info("字符串[{}]转枚举", type);
            //判断传过来的枚举字符串是不是 BaseEntryCommon类型
            if(type.contains("{") || type.contains("}")){
                BaseEntryEnum commonEnum = JsonUtil.toBean(type, BaseEntryEnum.class);
                type = String.valueOf(commonEnum.getType());
            }

            for (T t : values) {
                if (String.valueOf(t.getType()).equals(type)) {
                    return t;
                }
            }
            return null;
        }
    }
}

