package com.github.chenjianhua.springbootrocketmq.config;

import com.github.chenjianhua.common.json.util.JsonUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author chenjianhua
 * @date 2021/6/27
 */
@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "rocketmq.consumer")
public class RocketmqConsumerConfig {

    private String namesrvAddr;

    private String groupName;

    private int consumeThreadMin;

    private int consumeThreadMax;
    /**
     * 订阅指定的 topic
     */
    private String topics;

    /**
     * 订阅指定的 tag 默认的为* topic下的全部
     */
    private String tag = "*";

    private int consumeMessageBatchMaxSize;

    @Autowired
    private MqMessageListenerProcessor mqMessageListenerProcessor;

    @Bean
    @ConditionalOnMissingBean
    public DefaultMQPushConsumer defaultMQPushConsumer() throws RuntimeException {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeThreadMin(consumeThreadMin);
        consumer.setConsumeThreadMax(consumeThreadMax);

        // 设置 consumer 第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        // 设置消费模型，集群还是广播，默认为集群
        consumer.setMessageModel(MessageModel.CLUSTERING);
        // 设置一次消费消息的条数，默认为 1 条
        consumer.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);

        // Register callback to execute on arrival of messages fetched from brokers.
        consumer.registerMessageListener(mqMessageListenerProcessor);
        try {
            // 设置该消费者订阅的主题和tag，如果是订阅该主题下的所有tag，使用*；
            log.info("订阅主题:{} tag:{}", topics, tag);
            consumer.subscribe(topics, tag);
            // 启动消费
            consumer.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return consumer;
    }
}