package com.github.chenjianhua.rabbitmqconsumer;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class ListenerService implements Runnable{
	private static final String COMMAND_LIST = "list";
	private static final String COMMAND_EXIT = "exit";
	private final static String REGISTER_QUEUE = "register_queue";
	private final static String MSG_QUEUE = "msg_queue";
	
	private String appName;

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public void run(){
		try{	
			ConnectionFactory factory = new ConnectionFactory();
	        factory.setHost("localhost");
	        Connection connection = factory.newConnection();
	        Channel channel = connection.createChannel();

	        channel.exchangeDeclare(MSG_QUEUE, "direct");
	        String queueName = channel.queueDeclare().getQueue();
	        
	       // for(String channelStr : strArr){
	        channel.queueBind(queueName, MSG_QUEUE, appName);

	        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

	        QueueingConsumer consumer = new QueueingConsumer(channel);
	        channel.basicConsume(queueName, true, consumer);

	        while (true) {
	          QueueingConsumer.Delivery delivery = consumer.nextDelivery();
	          String message = new String(delivery.getBody());

	          System.out.println(" [x] Received '" + message + "'");
	          if(message.equals(COMMAND_EXIT)){
		          System.out.println("收到退出命令,准备退出!");
		          connection.close();
		          break;
	          }
	        }
		}catch(Exception e){
			e.printStackTrace(); 
		}

	}
}
