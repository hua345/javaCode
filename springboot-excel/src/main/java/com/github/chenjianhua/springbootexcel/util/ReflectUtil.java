package com.github.chenjianhua.springbootexcel.util;

import com.github.chenjianhua.springbootexcel.bo.TableFieldInfoBo;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * @author chenjianhua
 * @date 2021/3/16
 */
@Slf4j
public class ReflectUtil {
    public static Object getObjectValue(Object obj, TableFieldInfoBo fieldItem) {
        Object value = null;
        try {
            Field field = obj.getClass().getDeclaredField(fieldItem.getFieldCode());
            field.setAccessible(true);
            value = field.get(obj);
        } catch (NoSuchFieldException e) {
            log.error("没有这个字段信息:{}", fieldItem.getFieldCode());
        } catch (IllegalArgumentException e) {
            log.error("没有这个字段信息:{}", fieldItem.getFieldCode());
        } catch (IllegalAccessException e) {
            log.error("没有这个字段访问权限:{}", fieldItem.getFieldCode());
        }
        return value;
    }
}
