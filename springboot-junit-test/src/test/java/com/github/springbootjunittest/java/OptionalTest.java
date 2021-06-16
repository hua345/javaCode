package com.github.springbootjunittest.java;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author chenjianhua
 * @date 2021/5/18
 */
@Slf4j
public class OptionalTest {

    public static final String NO_EXIST_KEY = "name";
    public static final String HELLO_WORLD = "hello world";

    public static final String EXCEPTION_MSG = "没有查询到数据";

    @Test
    public void testOptional() {
        Map<String, String> map = new HashMap<>();
        Optional.ofNullable(map.get(NO_EXIST_KEY)).ifPresent(name -> {
            Assertions.assertNotNull(name);
        });
        Optional<String> nameOpt = Optional.ofNullable(map.get(NO_EXIST_KEY));
        nameOpt.ifPresent(name -> {
            Assertions.assertNotNull(name);
        });
        map.put(NO_EXIST_KEY, HELLO_WORLD);
        Optional.ofNullable(map.get(NO_EXIST_KEY)).ifPresent(name -> {
            Assertions.assertEquals(name, HELLO_WORLD);
        });
        nameOpt = Optional.ofNullable(map.get(NO_EXIST_KEY));
        nameOpt.ifPresent(name -> {
            Assertions.assertEquals(name, HELLO_WORLD);
        });
    }

    @Test
    public void testOptionalOrElse() {
        Map<String, String> map = new HashMap<>();
        String testStr = Optional.ofNullable(map.get(NO_EXIST_KEY)).orElse(HELLO_WORLD);
        Assertions.assertEquals(HELLO_WORLD, testStr);
    }

    @Test
    public void testOptionalException() {
        Map<String, String> map = new HashMap<>();
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            Optional.ofNullable(map.get(NO_EXIST_KEY)).orElseThrow(() -> new RuntimeException(EXCEPTION_MSG));
        });
        Assertions.assertEquals(EXCEPTION_MSG, exception.getMessage());
    }

}
