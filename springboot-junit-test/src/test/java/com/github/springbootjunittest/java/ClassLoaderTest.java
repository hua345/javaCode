package com.github.springbootjunittest.java;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author chenjianhua
 * @date 2021/5/14
 */
@Slf4j
public class ClassLoaderTest {
    @Test
    public void classLoaderTest() {
        ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
        Assertions.assertEquals("sun.misc.Launcher$AppClassLoader", classLoader.getClass().getName());
        Assertions.assertEquals("sun.misc.Launcher$ExtClassLoader", classLoader.getParent().getClass().getName());
        Assertions.assertEquals("sun.misc.Launcher$AppClassLoader", ClassLoader.getSystemClassLoader().getClass().getName());
        Assertions.assertEquals("sun.misc.Launcher$ExtClassLoader", ClassLoader.getSystemClassLoader().getParent().getClass().getName());
        Assertions.assertNull(ClassLoader.getSystemClassLoader().getParent().getParent());
    }
}
