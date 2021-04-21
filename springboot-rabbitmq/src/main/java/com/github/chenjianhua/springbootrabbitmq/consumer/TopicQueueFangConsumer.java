package com.github.chenjianhua.springbootrabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * @author chenjianhua
 * @date 2021/4/12
 */
@Slf4j
public class TopicQueueFangConsumer {
    private static final String EXCHANGE_NAME = "fangTopic";
    private static final String QUEUE_NAME = "fangTopicQue";
    private static final String BINDING_KEY = "fang.*";

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = QUEUE_NAME, durable = "true", autoDelete = "false"),
            exchange = @Exchange(value = EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
            key = BINDING_KEY))
    public void process(String message) {
        log.info("{}收到消息:{}", QUEUE_NAME, message);
    }
}
