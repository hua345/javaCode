package com.github.springbootjunittest.springboot;

import com.github.springbootjunittest.springboot.aop.DemoAnnotationService;
import com.github.springbootjunittest.springboot.aop.DemoMethodService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SpringbootAopTest {

    @Resource
    private DemoMethodService demoMethodService;

    @Resource
    private DemoAnnotationService demoAnnotationService;

    @Test
    void contextLoads() {
        demoMethodService.add();
        demoAnnotationService.add();
    }
}
