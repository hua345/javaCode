package com.github.chenjianhua.springbootrabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author chenjianhua
 * @date 2021/4/13
 */
@Slf4j
@Component
public class DirectQueueNoPrefetchCountConsumer {
    private static final String EXCHANGE_NAME = "fangDirect";
    private static final String QUEUE_NAME = "fangDirectQuePrefetchCount";
    private static final String BINDING_KEY = "prefetchCount";

    /**
     * https://www.rabbitmq.com/consumer-prefetch.html
     * prefetchCount配置和basicQos(int prefetchCount, boolean global)中是一样的
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = QUEUE_NAME, durable = "true", autoDelete = "false"),
            exchange = @Exchange(value = EXCHANGE_NAME, type = ExchangeTypes.DIRECT),
            key = BINDING_KEY))
    public void process2(String message) {
        log.info("{} 没有prefetchCount 收到消息:{}", QUEUE_NAME, message);
    }
}

