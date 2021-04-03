package com.github.springbootjunittest.springboot.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author chenjianhua
 * @date 2021/4/3
 */
@Slf4j
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        // 这边只做简单打印   原样返回bean
        log.info("postProcessBeforeInitialization====" + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        // 这边只做简单打印   原样返回bean
        log.info("postProcessAfterInitialization====" + beanName);
        return bean;
    }
}