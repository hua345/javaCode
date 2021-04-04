package com.github.springbootjunittest.springboot;

import com.github.springbootjunittest.springboot.aop.DemoAnnotationService;
import com.github.springbootjunittest.springboot.aop.DemoMethodService;
import com.github.springbootjunittest.springboot.event.DemoPublisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SpringbootEventTest {

    @Autowired
    private DemoPublisher demoPublisher;

    @Test
    void contextLoads() {
        demoPublisher.publish("fang");
    }
}
