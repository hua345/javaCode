package com.github.springbootjunittest.java.reflect;

import com.github.chenjianhua.common.json.util.JsonUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author chenjianhua
 * @date 2021/5/26
 */
@Slf4j
public class CompareObjUtil {
    @Data
    public final static class DiffValue {
        private Object fieldsValue1;
        private Object fieldsValue2;
    }

    public static Map<String, DiffValue> compareModelFields(Object dbData, Object updateData, String... ignoreFields) {
        return compareFields(dbData, updateData, ignoreFields, "id", "creator", "updator", "createAt", "updateAt");
    }

    public static Map<String, DiffValue> compareFields(Object dbData, Object updateData, String... ignoreFields) {
        return compareFields(dbData, updateData, null, ignoreFields);
    }

    /**
     * 比较两个实体属性值，返回一个map以有差异的属性名为key，value为一个Map分别存oldObject,newObject此属性名的值
     *
     * @param dbData        进行属性比较的对象1
     * @param updateData    进行属性比较的对象2
     * @param ignoreFields1 需要忽略的字段
     * @return 属性差异比较结果map
     */
    @SuppressWarnings("rawtypes")
    public static Map<String, DiffValue> compareFields(Object dbData, Object updateData, String[] ignoreFields1, String... ignoreFields2) {
        try {
            Map<String, DiffValue> map = new HashMap<>(16);
            if (null == dbData && null != updateData) {
                DiffValue diffValue = new DiffValue();
                diffValue.setFieldsValue1(null);
                diffValue.setFieldsValue2("对更新数据进行保存:" + JsonUtil.toJsonString(updateData));
                map.put("没有查询到数据", diffValue);
                return map;
            }
            if (null != dbData && null == updateData) {
                throw new RuntimeException("更新数据不能为空");
            }
            // 将忽略的字段转换为set
            Set<String> ignoreFieldsSet = new HashSet<>();
            if (null != ignoreFields1) {
                ignoreFieldsSet.addAll(Arrays.asList(ignoreFields1));
            }
            if (null != ignoreFields2) {
                ignoreFieldsSet.addAll(Arrays.asList(ignoreFields2));
            }

            // 只有两个对象都是同一类型的才有可比性
            if (dbData.getClass() == updateData.getClass()) {
                Class clazz = dbData.getClass();
                // 获取object的属性描述
                PropertyDescriptor[] pds = Introspector.getBeanInfo(clazz,
                        Object.class).getPropertyDescriptors();
                // 这里就是所有的属性了
                for (PropertyDescriptor pd : pds) {
                    // 属性名
                    String name = pd.getName();
                    // 如果当前属性选择忽略比较，跳到下一次循环
                    if (ignoreFieldsSet.contains(name)) {
                        continue;
                    }
                    // get方法
                    Method readMethod = pd.getReadMethod();
                    // 在obj1上调用get方法等同于获得obj1的属性值
                    Object fieldValue1 = readMethod.invoke(dbData);
                    // 在obj2上调用get方法等同于获得obj2的属性值
                    Object fieldValue2 = readMethod.invoke(updateData);
                    if (fieldValue1 == null && fieldValue2 == null) {
                        continue;
                    } else if (fieldValue1 == null && fieldValue2 != null) {
                        DiffValue diffValue = new DiffValue();
                        diffValue.setFieldsValue1(null);
                        diffValue.setFieldsValue2(fieldValue2);
                        map.put(name, diffValue);
                        continue;
                    }
                    // 比较这两个值是否相等,不等就可以放入map了
                    if (!fieldValue1.equals(fieldValue2)) {
                        DiffValue diffValue = new DiffValue();
                        diffValue.setFieldsValue1(fieldValue1);
                        diffValue.setFieldsValue2(fieldValue2);
                        map.put(name, diffValue);
                    }
                }
            }
            return map;
        } catch (Exception e) {
            log.error("比较对象出现异常:{}", e);
            throw new RuntimeException("比较对象出现异常");
        }
    }
}
