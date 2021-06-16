package com.github.springbootjunittest.java.template;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ResolvableType;

/**
 * @author chenjianhua
 * @date 2021/6/15
 */
@Slf4j
public class ResolvableTypeTest {
    @Test
    public void testResolvableType() {
        // 获取父类型
        ResolvableType superType = ResolvableType.forClass(Student.class).getSuperType();
        // 获取泛型类型
        ResolvableType[] superGenericsType = superType.getGenerics();
        Assertions.assertEquals(superGenericsType[0].resolve(), String.class);
        Assertions.assertEquals(superGenericsType[1].resolve(), Integer.class);
    }
}
