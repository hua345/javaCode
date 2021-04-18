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
    /**
     * https://www.rabbitmq.com/consumer-prefetch.html
     * prefetchCount配置和basicQos(int prefetchCount, boolean global)中是一样的
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "fangDirectQuePrefetchCount", durable = "true", autoDelete = "false"),
            exchange = @Exchange(value = "fangDirect", type = ExchangeTypes.DIRECT),
            key = "prefetchCount"))
    public void process2(String message) {
        log.info("fangDirectQuePrefetchCount 没有prefetchCount 收到消息:{}", message);
    }
}

