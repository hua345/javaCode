package com.github.springbootjunittest.springboot.beanscan;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author chenjianhua
 * @date 2021/5/7
 */
public class MyClassPathDefinitionScanner extends ClassPathBeanDefinitionScanner {

    private Class type;

    public MyClassPathDefinitionScanner(BeanDefinitionRegistry registry, Class<? extends Annotation> type) {
        super(registry, false);
        this.type = type;
    }

    /**
     * 注册 过滤器
     */
    public void registerTypeFilter() {
        addIncludeFilter(new AnnotationTypeFilter(type));
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        return super.doScan(basePackages);
    }
}
