package com.github.springbootjunittest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author chenjianhua
 * @date 2020/9/27
 */
@Slf4j
public class StringTest {
    private final static String testName = "fang";

    private final static String testNUll = null;

    @Test
    public void StringBuilderTest() {
        StringBuilder sb = new StringBuilder();
        sb.append(testName).append(testNUll);
        assertEquals("fangnull", sb.toString());
    }
}
