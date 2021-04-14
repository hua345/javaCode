package com.github.chenjianhua.springbootrabbitmq.consumer;

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
public class DirectQueueConsumer {

    /**
     * exchange, queue 已存在,直接指定注解中的queues参数即可
     * 当发送的消息，Properties属性中content_type类型为text时可以直接用String接收
     */
    @RabbitListener(queues = {"fangDirectQue"})
    public void process(String message) {
        log.info("fangDirectQue收到消息:{}", message);
    }

    /**
     * 此时不管属性中有没有content_type属性都能接收到数据
     */
    @RabbitListener(queues = {"loveDirectQue", "fangDirectQue"})
    public void handleMessage(Message message) {
        if ("loveDirectQue".equals(message.getMessageProperties().getConsumerQueue())) {
            log.info("消费的消息来自的队列名为:{}", message.getMessageProperties().getConsumerQueue());
        } else if ("fangDirectQue".equals(message.getMessageProperties().getConsumerQueue())) {
            log.info("消费的消息来自的队列名为:{}", message.getMessageProperties().getConsumerQueue());
        }
        log.info("{} message properties:{}", message.getMessageProperties().getConsumerQueue(), message.getMessageProperties());
        log.info("{} message body:{}", message.getMessageProperties().getConsumerQueue(), new String(message.getBody()));
    }
}
