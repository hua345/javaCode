package com.github.springbootjunittest.springboot;

import com.github.springbootjunittest.springboot.beanscan.MyClassPathDefinitionScanner;
import com.github.springbootjunittest.springboot.beanscan.MyScanAnnotation;
import com.github.springbootjunittest.springboot.beanscan.MyScanTestBean;
import com.github.springbootjunittest.springboot.event.DemoPublisher;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author chenjianhua
 * @date 2021/5/7
 */
@Slf4j
@SpringBootTest
public class SpringbootBeanScanTest {
    @Autowired
    private MyScanTestBean myScanTestBean;

    @Test
    void contextLoads() {
        log.info("{}", myScanTestBean.getClass());
    }

    @Test
    public void testSimpleScan() {
        String BASE_PACKAGE = "com.github.springbootjunittest.springboot";
        GenericApplicationContext context = new GenericApplicationContext();
        MyClassPathDefinitionScanner myClassPathDefinitionScanner = new MyClassPathDefinitionScanner(context, MyScanAnnotation.class);
        // 注册过滤器
        myClassPathDefinitionScanner.registerTypeFilter();
        int beanCount = myClassPathDefinitionScanner.scan(BASE_PACKAGE);
        context.refresh();
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        log.info("扫描bean数量:{}", beanCount);
        for (String beanDefinitionName : beanDefinitionNames) {
            log.info(beanDefinitionName);
        }
    }
}
