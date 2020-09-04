package com.chenjianhua.springcloudkafka.comsumer;

import com.chenjianhua.springcloudkafka.KafkaConstants;
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
public class KafkaConsumerService {
    @KafkaListener(topics = {KafkaConstants.DEFAULT_TOPIC}, groupId = KafkaConstants.DEFAULT_GROUP_ID, clientIdPrefix = KafkaConstants.DEFAULT_TOPIC + "01")
    public void consumer01(ConsumerRecord<Integer, String> integerStringConsumerRecords) {
        log.info("consumer01:{}", integerStringConsumerRecords.toString());
    }

    @KafkaListener(topics = {KafkaConstants.DEFAULT_TOPIC}, groupId = KafkaConstants.DEFAULT_GROUP_ID, clientIdPrefix = KafkaConstants.DEFAULT_TOPIC + "02")
    public void consumer02(ConsumerRecord<Integer, String> integerStringConsumerRecords) {
        log.info("consumer02:{}", integerStringConsumerRecords.toString());
    }
}