package com.github.chenjianhua.springbootelasticsearch;

import com.github.chenjianhua.springbootelasticsearch.model.JdProduct;
import com.github.chenjianhua.springbootelasticsearch.util.HtmlParseUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author chenjianhua
 * @date 2020/10/9
 */
@Slf4j
@SpringBootTest
public class HtmlParseTest {
    @Test
    public void testJdProduct() throws Exception {
        List<JdProduct> products = HtmlParseUtils.listJdGoods("牛奶");
        log.info(products.toString());
    }
}
