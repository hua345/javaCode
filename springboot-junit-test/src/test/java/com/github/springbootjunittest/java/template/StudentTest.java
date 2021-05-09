package com.github.springbootjunittest.java.template;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.core.ResolvableType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author chenjianhua
 * @date 2021/5/8
 */
@Slf4j
class StudentTest {

    @Test
    public void testJavaParameterizedType() {
        // 对于继承的父类是泛型的情况
        ParameterizedType genericSuperclass = (ParameterizedType) Student.class.getGenericSuperclass();
        log.info("{}", genericSuperclass);
        Type[] type = genericSuperclass.getActualTypeArguments();
        log.info("{}", genericSuperclass.getActualTypeArguments());
        log.info("{}", genericSuperclass.getActualTypeArguments()[1]);
    }

    @Test
    public void testInterfaceJavaParameterizedType() {
        // 对于继承的父类是泛型的情况
        ParameterizedType genericSuperclass = (ParameterizedType) Huawei.class.getGenericInterfaces()[0];
        log.info("{}", genericSuperclass);
        Type[] type = genericSuperclass.getActualTypeArguments();
        log.info("{}", genericSuperclass.getActualTypeArguments());
        log.info("{}", genericSuperclass.getActualTypeArguments()[1]);
    }


    @Test
    public void testSpringResolvableType() {
        // Spring的提供工具类,用于获取继承的父类是泛型的信息
        ResolvableType resolvableType = ResolvableType.forClass(Student.class);
        log.info("{}", resolvableType);
        Class<?> resolve = resolvableType.getSuperType().getGeneric(1).resolve();
        log.info("{}", resolvableType.getSuperType().getGeneric(0).resolve());
        log.info("{}", resolvableType.getSuperType().getGenerics());
        log.info("{}", resolvableType.getSuperType().getGeneric(1).resolve());
    }

    @Test
    public void testInterfaceSpringResolvableType() {
        // Spring的提供工具类,用于获取继承的父类是泛型的信息
        ResolvableType resolvableType = ResolvableType.forClass(Huawei.class);
        log.info("{}", resolvableType);
        Class<?> resolve = resolvableType.getInterfaces()[0].getGeneric(1).resolve();
        log.info("{}", resolvableType.getInterfaces()[0].getGeneric(0).resolve());
        log.info("{}", resolvableType.getInterfaces()[0].getGenerics());
        log.info("{}", resolvableType.getInterfaces()[0].getGeneric(1).resolve());
    }
}