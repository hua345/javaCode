package com.github.springbootjunittest.springboot;

import com.github.springbootjunittest.springboot.beanscan.MyScanTestBean;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author chenjianhua
 * @date 2021/5/7
 */
@Slf4j
@SpringBootTest
public class SpringbootBeanScanTest {
    @Autowired
    private MyScanTestBean myScanTestBean;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(myScanTestBean);
        Assertions.assertEquals(MyScanTestBean.class, myScanTestBean.getClass());
    }
}
