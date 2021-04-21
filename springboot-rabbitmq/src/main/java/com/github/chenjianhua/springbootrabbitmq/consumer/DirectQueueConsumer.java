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
    private static final String QUEUE_NAME_FANG = "fangDirectQue";
    private static final String QUEUE_NAME_LOVE = "fangDirectQue";

    /**
     * exchange, queue 已存在,直接指定注解中的queues参数即可
     * 当发送的消息，Properties属性中content_type类型为text时可以直接用String接收
     */
    @RabbitListener(queues = {QUEUE_NAME_FANG})
    public void process(String message) {
        log.info("{}收到消息:{}", QUEUE_NAME_FANG, message);
    }

    /**
     * 此时不管属性中有没有content_type属性都能接收到数据
     */
    @RabbitListener(queues = {QUEUE_NAME_LOVE, QUEUE_NAME_FANG})
    public void handleMessage(Message message) {
        if (QUEUE_NAME_LOVE.equals(message.getMessageProperties().getConsumerQueue())) {
            log.info("消费的消息来自的队列名为:{}", message.getMessageProperties().getConsumerQueue());
        } else if (QUEUE_NAME_FANG.equals(message.getMessageProperties().getConsumerQueue())) {
            log.info("消费的消息来自的队列名为:{}", message.getMessageProperties().getConsumerQueue());
        }
        log.info("{} message properties:{}", message.getMessageProperties().getConsumerQueue(), message.getMessageProperties());
        log.info("{} message body:{}", message.getMessageProperties().getConsumerQueue(), new String(message.getBody()));
    }
}
