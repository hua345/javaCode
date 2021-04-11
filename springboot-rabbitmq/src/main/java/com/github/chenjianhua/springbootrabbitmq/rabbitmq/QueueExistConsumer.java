package com.github.chenjianhua.springbootrabbitmq.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author chenjianhua
 * @date 2021/4/9
 */
@Slf4j
@Component
public class QueueExistConsumer {

    /**
     * exchange, queue 已存在,直接指定注解中的queues参数即可
     */
    @RabbitListener(queues = {"fangDirectQue"})
    public void process(String message) {
        log.info("收到消息:{}", message);
    }
}
