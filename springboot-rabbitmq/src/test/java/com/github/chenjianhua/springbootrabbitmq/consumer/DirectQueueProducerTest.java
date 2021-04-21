package com.github.chenjianhua.springbootrabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

/**
 * @author chenjianhua
 * @date 2021/4/14
 */
@Slf4j
@SpringBootTest
class DirectQueueProducerTest {
    private static final String EXCHANGE_NAME = "fangDirect";
    private static final String ROUTING_KEY = "love";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void testDirectQueueProducer() {
        for (int i = 1; i <= 10; i++) {
            // 每个发送的消息都需要配备一个 CorrelationData 相关数据对象，用来表示当前消息唯一性
            CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
            log.info("correlationDataId:{}", correlationData.getId());
            String message = "消息" + i;
            log.info("{} {}发送消息：", EXCHANGE_NAME, ROUTING_KEY, message);
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, message, correlationData);
        }
    }
}