package com.chenjianhua.springcloudkafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.internals.DefaultPartitioner;
import org.apache.kafka.common.utils.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@Slf4j
@SpringBootTest
class SpringCloudKafkaApplicationTests {
    @Autowired
    private KafkaProducerService kafkaProducerService;

    private static final String TestKey1 = "fang";
    private static final String TestKey2 = "Hua";

    @Test
    public void testPartitioner() {
        DefaultPartitioner defaultPartitioner = new DefaultPartitioner();
        log.info("partitioner:{}", Utils.toPositive(Utils.murmur2(TestKey1.getBytes())) % 2);
        log.info("partitioner:{}", Utils.toPositive(Utils.murmur2(TestKey2.getBytes())) % 2);
    }

    @Test
    public void testSendAsync() {
        for (int i = 0; i < 10; i++) {
            kafkaProducerService.asyncSend(KafkaConstants.DEFAULT_TOPIC, TestKey1, "asyncFang0" + i);
        }
    }

    @Test
    public void testSendSync() {
        for (int i = 0; i < 10; i++) {
            kafkaProducerService.syncSend(KafkaConstants.DEFAULT_TOPIC, "syncFang0" + i);
        }
    }
}
