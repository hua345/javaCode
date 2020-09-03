package com.chenjianhua.springcloudkafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author chenjianhua
 * @date 2020/9/3
 */
@Component
@Slf4j
public class KafkaConsumer {
    @KafkaListener(topics = {KafkaConstants.DEFAULT_TOPIC}, groupId = KafkaConstants.DEFAULT_GROUP_ID)
    public void consumer01(ConsumerRecord<Integer, String> integerStringConsumerRecords) {
        log.info("consumer01:{}", integerStringConsumerRecords.value());
    }
}