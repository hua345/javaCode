package com.github.chenjianhua.rabbitmqproducer;

import java.util.Scanner;


public class CommandService implements Runnable{
	private static final String COMMAND_LIST = "list";
	private static final String COMMAND_EXIT = "exit";
	public void run(){
    	Scanner sc = new Scanner(System.in);
    	while(true){
        	System.out.println("请输入命令：list or exit");
        	String command = sc.next();
        	if(COMMAND_LIST.equalsIgnoreCase(command)){
        		System.out.println("Command :" + COMMAND_LIST);
        	}else if(COMMAND_EXIT.equalsIgnoreCase(command)){
        		System.out.println("Command :" + COMMAND_EXIT);
        		System.out.println("请输入需要断开的客户端名称:");
        		String clientName = sc.next();
        		System.out.println(clientName + "已经断开!");
        	}
    	}
	}
}
