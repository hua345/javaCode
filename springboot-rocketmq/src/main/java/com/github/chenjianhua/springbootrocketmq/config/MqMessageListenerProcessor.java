package com.github.chenjianhua.springbootrocketmq.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author chenjianhua
 * @date 2021/6/27
 */
@Slf4j
@Component
public class MqMessageListenerProcessor implements MessageListenerConcurrently {
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        if (CollectionUtils.isEmpty(list)) {
            log.info("收到的消息为空");
        } else {
            /**
             *   业务逻辑
             */
            log.info("收到了消息=====" + new String(list.get(0).getBody()));
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}