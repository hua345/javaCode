package com.github.springbootjunittest.springboot.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author chenjianhua
 * @date 2021/4/4
 */
@Slf4j
@Component
public class DemoPublisher {
    @Resource
    private ApplicationContext applicationContext;

    public void publish(String message) {
        log.info("发送消息:{}", message);
        applicationContext.publishEvent(new DemoEvent(this, message));
    }
}