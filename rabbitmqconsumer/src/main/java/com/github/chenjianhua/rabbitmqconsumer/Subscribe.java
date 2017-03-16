package com.github.chenjianhua.rabbitmqconsumer;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class Subscribe {
	private final static String FANOUT_EXCHANGE_NAME = "fanoutInfo";
	private final static String DIRECT_EXCHANGE_NAME = "directInfo";
	private final static String TOPIC_EXCHANGE_NAME = "topicInfo";
	private final static String HEADERS_EXCHANGE_NAME = "headersInfo";
	
	public void fanoutConsume() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(FANOUT_EXCHANGE_NAME, "fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, FANOUT_EXCHANGE_NAME, "");
        
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);

        while (true) {
          QueueingConsumer.Delivery delivery = consumer.nextDelivery();
          String message = new String(delivery.getBody());

          System.out.println(" [x] Received '" + message + "'");   
        }
	}
	public void directConsume(String[] strArr) throws Exception {
        if(strArr.length < 1){
            System.err.println("Usage: ReceiveLogsDirect [chan1] [chan2] [chan3]");
            System.exit(1);
        }
		
		ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(DIRECT_EXCHANGE_NAME, "direct");
        String queueName = channel.queueDeclare().getQueue();
        
        for(String channelStr : strArr){
        	channel.queueBind(queueName, DIRECT_EXCHANGE_NAME, channelStr);
        }
        
        
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);

        while (true) {
          QueueingConsumer.Delivery delivery = consumer.nextDelivery();
          String message = new String(delivery.getBody());

          System.out.println(" [x] Received '" + message + "'");   
        }
	}
	public void topicConsume(String[] strArr) throws Exception {
        if(strArr.length < 1){
            System.err.println("Usage: ReceiveLogsDirect [binding_key]");
            System.exit(1);
        }
        Connection connection = null;
        Channel channel = null;
        try{
    		ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.exchangeDeclare(TOPIC_EXCHANGE_NAME, "topic");
            String queueName = channel.queueDeclare().getQueue();
            
            for(String channelStr : strArr){
            	channel.queueBind(queueName, TOPIC_EXCHANGE_NAME, channelStr);
            }
            
            
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.basicConsume(queueName, true, consumer);

            while (true) {
              QueueingConsumer.Delivery delivery = consumer.nextDelivery();
              String message = new String(delivery.getBody());

              System.out.println(" [x] Received '" + message + "'");   
            }
        }catch(Exception e){
        	e.printStackTrace();
        }finally {
            if (null != connection) {
                try {
                  connection.close();
                }
                catch (Exception ignore) {}
              }
            }

	}
}
