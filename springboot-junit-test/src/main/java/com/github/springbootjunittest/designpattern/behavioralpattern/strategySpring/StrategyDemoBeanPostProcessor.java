package com.github.springbootjunittest.designpattern.behavioralpattern.strategySpring;

import com.github.springbootjunittest.designpattern.behavioralpattern.strategy.TestStrategyEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenjianhua
 * @date 2021/5/24
 */
@Slf4j
@Component
public class StrategyDemoBeanPostProcessor implements BeanPostProcessor, Ordered {

    private final Set<Class<?>> nonAnnotatedClasses = Collections.newSetFromMap(new ConcurrentHashMap<>(64));

    private final TestAnnotationStrategyContext strategyContext;

    private StrategyDemoBeanPostProcessor(TestAnnotationStrategyContext context) {
        this.strategyContext = context;
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
        log.info("postProcessAfterInitialization");
        if (!this.nonAnnotatedClasses.contains(bean.getClass())) {
            // 获取使用 @MyStrategyAnnotation 注解的Class信息
            Class<?> targetClass = AopUtils.getTargetClass(bean);
            Class<AbstractTestAnnotationStrategy> orderStrategyClass = (Class<AbstractTestAnnotationStrategy>) targetClass;
            MyStrategyAnnotation ann = findAnnotation(targetClass);
            if (ann != null) {
                processListener(ann, orderStrategyClass);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        log.info("postProcessBeforeInitialization");
        return bean;
    }

    protected void processListener(MyStrategyAnnotation annotation,
                                   Class<AbstractTestAnnotationStrategy> classes) {
        // 注册策略
        this.strategyContext
                .registerStrategy(TestStrategyEnum.ALGORITHM_A, classes);
    }

    private MyStrategyAnnotation findAnnotation(Class<?> clazz) {

        MyStrategyAnnotation ann = AnnotatedElementUtils.findMergedAnnotation(clazz, MyStrategyAnnotation.class);
        return ann;
    }
}
