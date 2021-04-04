package com.github.springbootjunittest.springboot.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author chenjianhua
 * @date 2021/4/4
 */
@Slf4j
@Component
public class DemoListener implements ApplicationListener<DemoEvent> {
    /**
     * 使用onApplicationEvent接收消息
     */
    @Override
    public void onApplicationEvent(DemoEvent event) {
        String msg = event.getMessage();
        log.info("接收到的信息是：" + msg);
    }
}
