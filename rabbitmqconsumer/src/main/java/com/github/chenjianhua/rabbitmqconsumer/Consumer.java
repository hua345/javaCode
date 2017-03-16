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
        if(args.length < 1){
            System.err.println("请输入客户端用户名: [appName]");
            System.exit(1);
        }
        String appName = args[0];
    	ListenerService listenerService = new ListenerService();
    	listenerService.setAppName(appName);
    	Thread thread1 = new Thread(listenerService);
    	thread1.start();
    	RegisterService registerService = new RegisterService();
    	registerService.produceMsg(appName);

    }
}