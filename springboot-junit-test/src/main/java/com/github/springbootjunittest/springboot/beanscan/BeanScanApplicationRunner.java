package com.github.springbootjunittest.springboot.beanscan;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

/**
 * @author chenjianhua
 * @date 2021/3/22
 */
@Slf4j
@Component
public class BeanScanApplicationRunner implements ApplicationRunner, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("start scan annotation @MyScanAnnotation class.");
        strategyScan();
    }

    /**
     * 策略扫描
     */
    private void strategyScan() {
        Class<? extends Annotation> annotationClass = MyScanAnnotation.class;
        Map<String, Object> beanWithAnnotation = applicationContext.getBeansWithAnnotation(annotationClass);
        Set<Map.Entry<String, Object>> entitySet = beanWithAnnotation.entrySet();
        for (Map.Entry<String, Object> entry : entitySet) {
            Class<?> clazz = entry.getValue().getClass();
            MyScanAnnotation exportStrategyAnnotation = AnnotationUtils.findAnnotation(clazz, MyScanAnnotation.class);
            Object testAnnotationStrategy = applicationContext.getBean(clazz);
            log.info("ApplicationRunner scan:{} {}", exportStrategyAnnotation.name(), testAnnotationStrategy.getClass());
        }
    }
}