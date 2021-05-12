package com.github.springbootjunittest.springboot;

import com.github.springbootjunittest.springboot.spi.Phone;
import com.github.springbootjunittest.springboot.spi.SpringbootSpiService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Constructor;
import java.util.*;

/**
 * @author chenjianhua
 * @date 2021/5/8
 */
@Slf4j
public class SpringBootSPITest {
    @Test
    public void testJavaSpi() {
        ServiceLoader<Phone> phoneServiceLoader = ServiceLoader.load(Phone.class);
        phoneServiceLoader.forEach(provider -> {
            log.info("{} {}", provider.getClass().getName(), provider.getPhoneName());
        });
    }

    @Test
    public void testSpringBootSpi() {
        SpringbootSpiService springbootSpiService = new SpringbootSpiService();
        Collection<ApplicationContextInitializer> classList = springbootSpiService.getSpringFactoriesInstances(ApplicationContextInitializer.class);
        classList.forEach(item -> System.out.println(item));

        AnnotationConfigServletWebServerApplicationContext aa = new AnnotationConfigServletWebServerApplicationContext();
    }

}
