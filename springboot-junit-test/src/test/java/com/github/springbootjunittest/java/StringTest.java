package com.github.springbootjunittest.java;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author chenjianhua
 * @date 2020/9/27
 */
@Slf4j
public class StringTest {
    private final static String fangName = "fang";
    private final static String huaName = "hua";
    private final static String testName = fangName + "," + huaName;

    private final static String testNUll = null;

    @Test
    public void StringBuilderTest() {
        StringBuilder sb = new StringBuilder();
        sb.append(testName).append(testNUll);
        assertEquals(testName + "null", sb.toString());
    }

    @Test
    public void StringSplit() {
        String[] splitNames = testName.split(",");
        assertNotNull(splitNames);
        assertEquals(fangName, splitNames[0]);
        splitNames = StringUtils.split(testName, ",");
        assertNotNull(splitNames);
        assertEquals(fangName, splitNames[0]);
    }

    @Test
    public void StringSplit2() {
        String[] splitNames = fangName.split(",");
        assertNotNull(splitNames);
        assertEquals(fangName, splitNames[0]);
        splitNames = StringUtils.split(fangName, ",");
        assertNull(splitNames);
    }
}
