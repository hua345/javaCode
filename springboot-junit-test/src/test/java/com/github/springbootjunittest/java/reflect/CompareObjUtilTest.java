package com.github.springbootjunittest.java.reflect;

import com.github.chenjianhua.common.json.util.JsonUtil;
import com.github.springbootjunittest.java.Book;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author chenjianhua
 * @date 2021/5/26
 */
@Slf4j
class CompareObjUtilTest {
    @Test
    public void CompareObjTest() {
        Book book = new Book(1L, "架构整洁之道", BigDecimal.valueOf(22));
        Book book2 = new Book(2L, "数学之美", BigDecimal.valueOf(23));

        Map<String, CompareObjUtil.DiffValue> diffValueMap = CompareObjUtil.compareFields(null, book);
        log.info("diffValueMap:{}", JsonUtil.toJsonString(diffValueMap));

        diffValueMap = CompareObjUtil.compareFields(book, book2);
        log.info("diffValueMap:{}", JsonUtil.toJsonString(diffValueMap));
    }
}