package com.github.springbootjunittest.springboot;

import com.github.springbootjunittest.springboot.spi.Phone;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ServiceLoader;

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
}
