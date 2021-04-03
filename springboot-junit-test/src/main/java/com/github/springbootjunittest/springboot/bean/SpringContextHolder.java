package com.github.springbootjunittest.springboot.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

/**
 * @author chenjianhua
 * @date 2021/4/3
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext ctx;

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) {
        ctx = applicationContext;
    }

    /**
     * 根据注册的 beanName 找到在 Spring 中注册的 Bean
     *
     * @param beanName beanName
     * @return spring 中的单例
     */
    public static Object getBean(String beanName) {
        return ctx.getBean(beanName);
    }
}
