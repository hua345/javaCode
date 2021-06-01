//package com.github.springbootjunittest.springboot.beanscan;
//
//import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.context.ResourceLoaderAware;
//import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
//import org.springframework.core.io.ResourceLoader;
//import org.springframework.core.type.AnnotationMetadata;
//import org.springframework.core.type.filter.AnnotationTypeFilter;
//
///**
// * @author chenjianhua
// * @date 2021/5/7
// */
//public class MapperAutoConfigureRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {
//
//    private ResourceLoader resourceLoader;
//
//    private String BASE_PACKAGE = "com.github.springbootjunittest.springboot";
//
//    @Override
//    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
//        MyClassPathDefinitionScanner scanner = new MyClassPathDefinitionScanner(registry, MyScanAnnotation.class);
//        scanner.setResourceLoader(resourceLoader);
//        scanner.registerTypeFilter();
//        scanner.doScan(BASE_PACKAGE);
//    }
//
//    @Override
//    public void setResourceLoader(ResourceLoader resourceLoader) {
//        this.resourceLoader = resourceLoader;
//    }
//}