package com.github.springbootjunittest.springboot.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenjianhua
 * @date 2021/4/3
 */
@Slf4j
@Configuration
public class BookConfig {
    /**
     * 注解bean之指定init-method/destroy-method
     */
    @Bean(value = "bookConfigInitMethod", initMethod = "initBook", destroyMethod = "destroyBook")
    public BookConfigInitMethod getBookConfigInitMethod() {
        log.info("创建book实例");
        return new BookConfigInitMethod("数学之美");
    }

    /**
     * 实现InitializingBean/DisposableBean接口
     */
    @Bean
    public BookImplementMethod bookImplementMethod() {
        return new BookImplementMethod("刻意学习");
    }

    /**
     * 通过@PostConstruct和@PreDestroy注解
     */
    @Bean
    public BookAnnotation bookAnnotation() {
        return new BookAnnotation("非暴力沟通");
    }

    @Bean
    public MyBeanPostProcessor getMyBeanPostProcessor(){
        return new MyBeanPostProcessor();
    }
}