package com.github.chenjianhua.rabbitmqconsumer;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class RegisterService {
	private final static String REGISTER_QUEUE = "register_queue";
	private final static String MSG_QUEUE = "msg_queue";
	
	public void produceMsg() throws Exception {
        System.out.println( "Hello World!" );
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(REGISTER_QUEUE, false, false, false, null);
        String message = "Hello World!";
        channel.basicPublish("", REGISTER_QUEUE, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        
        channel.close();
        connection.close();
	}
}
