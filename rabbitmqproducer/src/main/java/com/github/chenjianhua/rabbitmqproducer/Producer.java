package com.github.chenjianhua.rabbitmqproducer;

public class Producer 
{

    public static void main( String[] args ) throws Exception 
    {
    	ClientInfo info = new ClientInfo();
    	CommandService commandService = new CommandService();
    	commandService.setInfo(info);
    	Thread thread1 = new Thread(commandService);
    	RegisterService registerService = new RegisterService();
    	registerService.setInfo(info);
    	Thread thread2 = new Thread(registerService);
    	thread1.start();
    	thread2.start();
    }
}
