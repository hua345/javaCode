package com.github.chenjianhua.rabbitmqproducer;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.util.Date; 
import java.text.SimpleDateFormat; 

public class RegisterService implements Runnable{
	private final static String REGISTER_QUEUE = "register_queue";
	private final static String MSG_QUEUE = "msg_queue";
	
	private ClientInfo info;
	
	public ClientInfo getInfo() {
		return info;
	}

	public void setInfo(ClientInfo info) {
		this.info = info;
	}

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
	          
	          Date nowTime=new Date(); 
	          SimpleDateFormat time=new SimpleDateFormat("yyyy年MM月dd日 HH时MM分ss秒"); 
	          Client client = new Client();
	          client.setID((info.getInfo().size() + 1) + "");
	          client.setName(message);
	          client.setRegister_date(time.format(nowTime));
	          info.addClient(client);
	          directProduce(message);
	        }
		}catch(Exception e){
			e.printStackTrace(); 
		}

	}
	public void directProduce(String appName) throws Exception{
	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();

	    channel.exchangeDeclare(MSG_QUEUE, "direct");
	    
	    String message = "Welcome " + appName + "!";

	    channel.basicPublish(MSG_QUEUE, appName, null, message.getBytes());
	    System.out.println(" [x] Sent '" + appName + ":"+ message + "'");

	    channel.close();
	    connection.close();
	}
}
