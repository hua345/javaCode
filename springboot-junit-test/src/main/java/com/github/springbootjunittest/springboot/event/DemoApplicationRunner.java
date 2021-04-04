package com.github.springbootjunittest.springboot.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author chenjianhua
 * @date 2021/4/4
 */
@Slf4j
@Component
public class DemoApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("spring boot启动后执行ApplicationRunner");
    }
}
