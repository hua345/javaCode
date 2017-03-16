package com.github.chenjianhua.rabbitmqproducer;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class RegisterService implements Runnable{
	private final static String REGISTER_QUEUE = "register_queue";
	private final static String MSG_QUEUE = "msg_queue";
	
	public void run(){
		try{
	        System.out.println( "Hello World!" );
	        
	        ConnectionFactory factory = new ConnectionFactory();
	        factory.setHost("localhost");
	        
	        Connection connection = factory.newConnection();
	        Channel channel = connection.createChannel();
	        
	        channel.queueDeclare(REGISTER_QUEUE, false, false, false, null);
	        
	        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
	        
	        QueueingConsumer consumer = new QueueingConsumer(channel);
	        channel.basicConsume(REGISTER_QUEUE, true, consumer);
	        
	        while (true) {
	          QueueingConsumer.Delivery delivery = consumer.nextDelivery();
	          String message = new String(delivery.getBody());
	          System.out.println(" [x] Received '" + message + "'");
	        }
		}catch(Exception e){
			e.printStackTrace(); 
		}

	}
}
