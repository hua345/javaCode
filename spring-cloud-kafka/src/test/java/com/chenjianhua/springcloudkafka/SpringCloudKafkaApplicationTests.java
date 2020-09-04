package com.chenjianhua.springcloudkafka;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@Slf4j
@SpringBootTest
class SpringCloudKafkaApplicationTests {
    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Test
    public void testSendAsync() {
        for (int i = 0; i < 10; i++) {
            kafkaProducerService.asyncSend(KafkaConstants.DEFAULT_TOPIC, "fang01", "asyncFang0" + i);
        }
    }

    @Test
    public void testSendSync() {
        for (int i = 0; i < 10; i++) {
            kafkaProducerService.syncSend(KafkaConstants.DEFAULT_TOPIC, "hua", "syncFang0" + i);
        }
    }
}
