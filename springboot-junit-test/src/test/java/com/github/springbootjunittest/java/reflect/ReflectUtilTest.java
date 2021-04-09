package com.github.springbootjunittest.java.reflect;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author chenjianhua
 * @date 2021/4/7
 */
@Data
@Slf4j
class ReflectUtilTest {
    private static final int TEST_TIMES = 10000 * 10000;
    private static final String TEST_NAME = "芳芳";

    public static TableFieldInfoBo tableFieldInfoBo;

    @BeforeAll
    public static void initData() {
        tableFieldInfoBo = new TableFieldInfoBo();
        tableFieldInfoBo.setFieldCode("name");
        tableFieldInfoBo.setFieldName("芳");
        tableFieldInfoBo.setName(TEST_NAME);
    }

    @Test
    public void testNormalValue() {
        for (int i = 0; i < TEST_TIMES; i++) {
            String name = tableFieldInfoBo.getName();
            assertEquals(TEST_NAME, name);
        }
    }

    @Test
    public void testReflectValue() {
        for (int i = 0; i < TEST_TIMES; i++) {
            String name = (String) ReflectUtil.getObjectValue(tableFieldInfoBo, tableFieldInfoBo);
            assertEquals(TEST_NAME, name);
        }
    }

    @Test
    public void testGetClassFieldValue() {
        for (int i = 0; i < TEST_TIMES; i++) {
            List<Object> name = ReflectUtil.getClassFieldValue(tableFieldInfoBo);
            assertEquals(TEST_NAME, name.get(2));
        }
    }

    @Test
    public void testBeanSet() {
        for (int i = 0; i < TEST_TIMES; i++) {
            TableFieldInfoBo item = new TableFieldInfoBo();
            item.setFieldCode(tableFieldInfoBo.getFieldCode());
            item.setFieldName(tableFieldInfoBo.getFieldName());
            item.setName(tableFieldInfoBo.getName());
            assertEquals(TEST_NAME, item.getName());
        }
    }

    @Test
    public void testBeanCopy() {
        for (int i = 0; i < TEST_TIMES; i++) {
            TableFieldInfoBo item = new TableFieldInfoBo();
            BeanUtils.copyProperties(tableFieldInfoBo, item);
            assertEquals(TEST_NAME, item.getName());
        }
    }
}