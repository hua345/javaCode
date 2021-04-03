package com.github.springbootjunittest.springboot;

import com.github.springbootjunittest.springboot.bean.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author chenjianhua
 * @date 2021/4/3
 */
@Slf4j
public class SpringbootBeanTest {
    @Test
    public void testBean() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BookConfig.class);
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for (String item : beanNames) {
            log.info("bean名称为===" + item);
        }
        BookConfigInitMethod bookConfigInitMethod = (BookConfigInitMethod) applicationContext.getBean("bookConfigInitMethod");
        log.info(bookConfigInitMethod.getName());
        BookImplementMethod bookImplementMethod = (BookImplementMethod) applicationContext.getBean("bookImplementMethod");
        log.info(bookImplementMethod.getName());
        BookAnnotation bookAnnotation = (BookAnnotation) applicationContext.getBean("bookAnnotation");
        log.info(bookAnnotation.getName());
    }
}
