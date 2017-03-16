package com.github.chenjianhua.rabbitmqproducer;

public class Producer 
{

    public static void main( String[] args ) throws Exception 
    {
    	CommandService commandService = new CommandService();
    	Thread thread1 = new Thread(commandService);
    	RegisterService registerService = new RegisterService();
    	Thread thread2 = new Thread(registerService);
    	thread1.start();
    	thread2.start();
//        OneWayProducer aa = new OneWayProducer();
//        aa.produceMsg();
//    	MultiWayProducer aa = new MultiWayProducer();
//    	aa.produceMsg(args);
    	//Publisher aa = new Publisher();
    	//aa.fanoutProduce(args);
    	//aa.directProduce(args);
    	//aa.topicProduce(args);
    }
}
