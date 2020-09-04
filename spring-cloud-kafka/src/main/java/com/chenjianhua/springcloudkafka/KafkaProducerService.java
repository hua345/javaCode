package com.chenjianhua.springcloudkafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.TimeUnit;

/**
 * @author chenjianhua
 * @date 2020/9/3
 */
@Slf4j
@Service
public class KafkaProducerService {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void asyncSend(String topic, String key, String msg) {
        //发送消息
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, key, msg);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> stringStringSendResult) {
                log.info("发送kafka topic:{}异步消息成功", topic);
            }

            @Override
            public void onFailure(Throwable throwable) {
                log.info("发送kafka topic:{}异步消息:{}失败", topic, msg);
            }
        });
    }

    public void asyncSend(String topic, String msg) {
        asyncSend(topic, null, msg);
    }

    public boolean syncSend(String topic, String key, String msg) {
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, key, msg);
        try {
            kafkaTemplate.send(producerRecord).get(5, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            log.info("发送kafka topic:{}同步消息:{}失败", topic, msg);
            return false;
        }
    }

    public boolean syncSend(String topic, String msg) {
        return syncSend(topic, null, msg);
    }
}
