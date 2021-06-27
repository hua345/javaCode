package com.github.chenjianhua.rocketmq.producer.config;

import lombok.Data;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author chenjianhua
 * @date 2021/6/27
 */
@Data
@Component
@ConfigurationProperties(prefix = "rocketmq.producer")
public class RocketmqProducerConfig {

    private String namesrvAddr;

    private String groupName;

    private Integer maxMessageSize;

    private Integer sendMsgTimeout;

    private Integer retryTimesWhenSendFailed;

    private Integer retryTimesWhenSendAsyncFailed;

    @Bean
    @ConditionalOnMissingBean
    public DefaultMQProducer defaultMQProducer() throws RuntimeException {
        DefaultMQProducer producer = new DefaultMQProducer(this.groupName);

        producer.setNamesrvAddr(this.namesrvAddr);
        // 如果topic不存在  自动创建topic
        producer.setCreateTopicKey("AUTO_CREATE_TOPIC_KEY");
        //如果发送消息的最大限制
        producer.setMaxMessageSize(this.maxMessageSize);
        //如果发送消息超时时间
        producer.setSendMsgTimeout(this.sendMsgTimeout);
        //如果发送消息失败，设置重试次数，默认为 2 次
        producer.setRetryTimesWhenSendFailed(this.retryTimesWhenSendFailed);
        //如果是异步发送消息失败，设置重试次数 默认为0次
        producer.setRetryTimesWhenSendAsyncFailed(retryTimesWhenSendAsyncFailed);
        try {
            producer.start();
        } catch (MQClientException e) {
            throw new RuntimeException(e);
        }
        return producer;
    }
}
