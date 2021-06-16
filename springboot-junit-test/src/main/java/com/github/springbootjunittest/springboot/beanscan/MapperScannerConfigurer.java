package com.github.springbootjunittest.springboot.beanscan;

import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

/**
 * @author chenjianhua
 * @date 2021/5/30
 */
@Setter
public class MapperScannerConfigurer implements BeanDefinitionRegistryPostProcessor, InitializingBean, ApplicationContextAware, BeanNameAware {
    private String basePackage;
    private String beanName;
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        BeanDefinition mapperScannerBean = ((ConfigurableApplicationContext) this.applicationContext).getBeanFactory().getBeanDefinition(this.beanName);
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        factory.registerBeanDefinition(this.beanName, mapperScannerBean);

        PropertyValues values = mapperScannerBean.getPropertyValues();
        this.basePackage = this.getPropertyValue("basePackage", values);

        MyClassPathDefinitionScanner scanner = new MyClassPathDefinitionScanner(beanDefinitionRegistry, MyScanAnnotation.class);
        scanner.registerTypeFilter();
        scanner.doScan(this.basePackage);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    private Environment getEnvironment() {
        return this.applicationContext.getEnvironment();
    }

    private String getPropertyValue(String propertyName, PropertyValues values) {
        PropertyValue property = values.getPropertyValue(propertyName);
        if (property == null) {
            return null;
        } else {
            Object value = property.getValue();
            if (value == null) {
                return null;
            } else if (value instanceof String) {
                return value.toString();
            } else {
                return value instanceof TypedStringValue ? ((TypedStringValue) value).getValue() : null;
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.basePackage, "Property 'basePackage' is required");
    }
}
