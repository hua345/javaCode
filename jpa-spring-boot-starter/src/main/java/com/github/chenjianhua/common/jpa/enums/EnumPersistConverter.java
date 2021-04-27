package com.github.chenjianhua.common.jpa.enums;

import com.github.common.enums.BaseEnum;

import javax.persistence.AttributeConverter;
import java.util.Objects;

/**
 * @author chenjianhua
 * @date 2020/9/7
 */
public class EnumPersistConverter<E extends Enum<E> & BaseEnum<T>, T> implements AttributeConverter<E, T> {
    private final Class<E> clazz;

    public EnumPersistConverter(Class<E> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T convertToDatabaseColumn(E attribute) {
        return Objects.nonNull(attribute.getType()) ? attribute.getType() : null;
    }

    @Override
    public E convertToEntityAttribute(T dbData) {
        if (Objects.isNull(dbData)) {
            return null;
        }

        E[] enums = clazz.getEnumConstants();
        for (E e : enums) {
            if (e.getType().equals(dbData)) {
                return e;
            }
        }

        throw new UnsupportedOperationException("枚举转化异常, 枚举[" + clazz.getSimpleName() + "],数据库库中的值为:[" + dbData + "]");
    }
}
