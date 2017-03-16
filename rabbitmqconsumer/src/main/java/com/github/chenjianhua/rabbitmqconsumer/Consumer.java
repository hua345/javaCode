package com.github.chenjianhua.rabbitmqconsumer;

/**
 * Hello world!
 *
 */
public class Consumer 
{
	private final static String QUEUE_NAME = "hello";
	private final static String REGISTER_QUEUE = "register_queue";
	private final static String MSG_QUEUE = "msg_queue";
	
    public static void main( String[] args ) throws Exception
    {
    	RegisterService aa = new RegisterService();
    	aa.produceMsg();
    }
}
