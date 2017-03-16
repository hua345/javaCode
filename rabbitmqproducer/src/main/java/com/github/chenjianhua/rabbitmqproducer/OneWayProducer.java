package com.github.chenjianhua.rabbitmqproducer;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class OneWayProducer {
	
	private final static String QUEUE_NAME = "hello";
	
	public void produceMsg() throws Exception {
        System.out.println( "Hello World!" );
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "Hello World!";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        
        channel.close();
        connection.close();
	}
}
