package com.github.chenjianhua.rabbitmqproducer;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class Publisher {
	private final static String FANOUT_EXCHANGE_NAME = "fanoutInfo";
	private final static String DIRECT_EXCHANGE_NAME = "directInfo";
	private final static String TOPIC_EXCHANGE_NAME = "topicInfo";
	private final static String HEADERS_EXCHANGE_NAME = "headersInfo";
	
	public void fanoutProduce(String[] strArr) throws Exception{
	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();

	    channel.exchangeDeclare(FANOUT_EXCHANGE_NAME, "fanout");

	    String message = getMessage(strArr);

	    channel.basicPublish(FANOUT_EXCHANGE_NAME, "", null, message.getBytes());
	    System.out.println(" [x] Sent '" + message + "'");

	    channel.close();
	    connection.close();
	}
	public void directProduce(String[] strArr) throws Exception{
	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();

	    channel.exchangeDeclare(DIRECT_EXCHANGE_NAME, "direct");
	    
	    String channelName = getChannel(strArr);
	    String message = getdirectMessage(strArr);

	    channel.basicPublish(DIRECT_EXCHANGE_NAME, channelName, null, message.getBytes());
	    System.out.println(" [x] Sent '" + channelName + ":"+ message + "'");

	    channel.close();
	    connection.close();
	}
	public void topicProduce(String[] strArr) throws Exception{
	    Connection connection = null;
	    Channel channel = null;
	    try{
		    ConnectionFactory factory = new ConnectionFactory();
		    factory.setHost("localhost");
		    connection = factory.newConnection();
		    channel = connection.createChannel();
		    
		    channel.exchangeDeclare(TOPIC_EXCHANGE_NAME, "topic");
		    
		    String channelName = getChannel(strArr);
		    String message = getdirectMessage(strArr);
		    
		    channel.basicPublish(TOPIC_EXCHANGE_NAME, channelName, null, message.getBytes());
		    System.out.println(" [x] Sent '" + channelName + ":"+ message + "'");

	    }catch(Exception e){
	    	e.printStackTrace();
	    } finally {
	    	if(null != connection){
	    		try{
		    		connection.close();
	    		}catch(Exception ignore){}

	    	}
	    }
	}
	  private static String getMessage(String[] strings){
		    if (strings.length < 1)
		            return "info: Hello World!";
	    return joinStrings(strings, " ");
	  }
		  
	  private static String joinStrings(String[] strings, String delimiter) {
	    int length = strings.length;
	    if (length == 0) return "";
	    StringBuilder words = new StringBuilder(strings[0]);
	    for (int i = 1; i < length; i++) {
	        words.append(delimiter).append(strings[i]);
	    }
	    return words.toString();
	  }
	  private static String getChannel(String[] strings){
		    if (strings.length < 1)
		            return "chan1";
	    return strings[0];
	  }
	  private static String getdirectMessage(String[] strings){
		    if (strings.length < 2)
		            return "Hello World!";
	    return joindirectStrings(strings, " ", 1);
	  }
		  
	  private static String joindirectStrings(String[] strings, String delimiter,int startIndex) {
		    int length = strings.length;
		    if (length == 0 ) return "";
		    if (length < startIndex ) return "";
		    StringBuilder words = new StringBuilder(strings[startIndex]);
		    for (int i = startIndex + 1; i < length; i++) {
		        words.append(delimiter).append(strings[i]);
		    }
		    return words.toString();
	  }
}
