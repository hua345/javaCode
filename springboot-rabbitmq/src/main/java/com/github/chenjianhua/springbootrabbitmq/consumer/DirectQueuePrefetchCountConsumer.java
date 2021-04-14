package com.github.chenjianhua.springbootrabbitmq.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author chenjianhua
 * @date 2021/4/13
 */
@Slf4j
@Component
public class DirectQueuePrefetchCountConsumer {
    /**
     * https://www.rabbitmq.com/consumer-prefetch.html
     * mq为每一个 consumer设置一个缓冲区，大小就是prefetch。每次收到一条消息，MQ会把消息推送到缓存区中，然后再推送给客户端
     * 假设prefetch值设为10，共有两个consumer。也就是说每个consumer每次会从queue中预抓取 10 条消息到本地缓存着等待消费。同时该channel的unacked数变为20。
     * 而Rabbit投递的顺序是，先为consumer1投递满10个message，再往consumer2投递10个message。
     * 如果这时有新message需要投递，先判断channel的unacked数是否等于20，如果是则不会将消息投递到consumer中，message继续呆在queue中。
     * 之后其中consumer对一条消息进行ack，unacked此时等于19，Rabbit就判断哪个consumer的unacked少于10，就投递到哪个consumer中。
     * <p>
     * prefetch_count参数仅仅在 basic.consume的 autoAck参数设置为 false的前提下才生效，也就是不能使用自动确认，自动确认的消息没有办法限流。
     * 我们经过压测,来判断consumer的消费能力，如果单位时间内，consumer到达的消息太多，也可能把消费者压垮。
     * 得到压测数据后，可以在@RabbitListener中配置prefetch count
     * 只需要在@RabbitListener中，用containerFactory指定一个监听器工厂类就行
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "fangDirectQuePrefetchCount", durable = "true", autoDelete = "false"),
            exchange = @Exchange(value = "fangDirect", type = ExchangeTypes.DIRECT),
            key = "prefetchCount"), containerFactory = "mqConsumerListenerContainer", ackMode = "MANUAL")
    public void process(String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws IOException, InterruptedException {
        log.info("fangDirectQuePrefetchCount {} 收到消息:{}", deliveryTag, message);
        Thread.sleep(100);
        channel.basicAck(deliveryTag, false);
    }
}

