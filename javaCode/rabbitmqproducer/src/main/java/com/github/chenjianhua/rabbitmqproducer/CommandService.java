package com.github.chenjianhua.rabbitmqproducer;

import java.util.Scanner;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.List;

public class CommandService implements Runnable{
	private static final String COMMAND_LIST = "list";
	private static final String COMMAND_EXIT = "exit";
	private final static String REGISTER_QUEUE = "register_queue";
	private final static String MSG_QUEUE = "msg_queue";
	
	private ClientInfo info;
	
	public ClientInfo getInfo() {
		return this.info;
	}

	public void setInfo(ClientInfo info) {
		this.info = info;
	}

	public void run(){
    	Scanner sc = new Scanner(System.in);
    	while(true){
        	System.out.println("请输入命令：list or exit");
        	String command = sc.next();
        	if(COMMAND_LIST.equalsIgnoreCase(command)){
        		displayInfo();
        		System.out.println("Command :" + COMMAND_LIST);
        	}else if(COMMAND_EXIT.equalsIgnoreCase(command)){
        		System.out.println("Command :" + COMMAND_EXIT);
        		System.out.println("请输入需要断开的客户端名称:");
        		String clientName = sc.next();
        		disconnectClient(clientName);
        		displayInfo();
        	}
    	}
	}
	public void displayInfo(){
		List<Client> list = this.info.getInfo();
		for(Client item : list){
			System.out.println(item.toString());
		}
	}
	public void disconnectClient(String appName) {
		List<Client> list = this.info.getInfo();
		boolean flag = false;
		for(Client item : list){
			if(item.getName().equals(appName)){
				flag = true;
				try{
					directProduce(appName);
				}catch(Exception e){
					e.printStackTrace();
				}
				info.deleteClient(appName);
				break;
			}
		}
		if(false == flag){
			System.out.println("输入的appName没有找到!");
		}
	}
	public void directProduce(String appName) throws Exception{
	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();

	    channel.exchangeDeclare(MSG_QUEUE, "direct");
	    
	    String message = COMMAND_EXIT;

	    channel.basicPublish(MSG_QUEUE, appName, null, message.getBytes());
	    System.out.println(" [x] Sent '" + appName + ":"+ message + "'");

	    channel.close();
	    connection.close();
	}
}
