package com.chenjianhua.springcloudkafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
class SpringCloudKafkaApplicationTests {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Test
    public void testSendAsync() {
        //发送消息
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(KafkaConstants.DEFAULT_TOPIC, "fang01");
        //回调函数
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> stringStringSendResult) {
                log.info("Send async message success");
            }

            @Override
            public void onFailure(Throwable throwable) {
                log.info("Send async message failed");
            }
        });
    }

    @Test
    public void testSendSync() {
        ProducerRecord<Object, Object> producerRecord = new ProducerRecord<>(KafkaConstants.DEFAULT_TOPIC, "fang02");
        try {
            kafkaTemplate.send(producerRecord).get(10, TimeUnit.SECONDS);
            log.info("Send sync message success");
        } catch (Exception e) {
            log.info("Send sync message failed");
        }
    }
}
