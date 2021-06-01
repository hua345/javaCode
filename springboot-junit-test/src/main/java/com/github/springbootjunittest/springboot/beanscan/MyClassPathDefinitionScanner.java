package com.github.springbootjunittest.springboot.beanscan;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.scope.ScopedProxyFactoryBean;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

/**
 * @author chenjianhua
 * @date 2021/5/7
 */
@Slf4j
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
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        if (beanDefinitions.isEmpty()) {
            log.warn("No MyScanAnnotation was found in '" + Arrays.toString(basePackages) + "' package. Please check your configuration.");
        } else {
            this.processBeanDefinitions(beanDefinitions);
        }

        return beanDefinitions;
    }

    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
        BeanDefinitionRegistry registry = this.getRegistry();
        Iterator beanDefinitionIt = beanDefinitions.iterator();
        while (beanDefinitionIt.hasNext()) {
            BeanDefinitionHolder holder = (BeanDefinitionHolder) beanDefinitionIt.next();
            AbstractBeanDefinition definition = (AbstractBeanDefinition) holder.getBeanDefinition();

            String beanClassName = definition.getBeanClassName();
            log.info("'Creating MapperFactoryBean with name '" + holder.getBeanName() + "' and '" + beanClassName + "' mapperInterface'");
        }
    }
}
