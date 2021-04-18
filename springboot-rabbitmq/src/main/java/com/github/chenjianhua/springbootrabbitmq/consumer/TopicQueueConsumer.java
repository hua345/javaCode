package com.github.chenjianhua.springbootrabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * @author chenjianhua
 * @date 2021/4/12
 */
@Slf4j
public class TopicQueueConsumer {
    /**
     * exchange, queue 已存在,直接指定注解中的queues参数即可
     * 当发送的消息，Properties属性中content_type类型为text时可以直接用String接收
     */
    @RabbitListener(queues = {"fangTopicQue", "loveTopicQue"})
    public void process(String message) {
        log.info("fangTopicQue收到消息:{}", message);
    }
}
