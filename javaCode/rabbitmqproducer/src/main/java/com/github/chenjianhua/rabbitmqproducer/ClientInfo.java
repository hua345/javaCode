package com.github.chenjianhua.rabbitmqproducer;

import java.util.List;
import java.util.ArrayList;

public class ClientInfo {
	private List<Client> info;
	private boolean flag = false;
	public ClientInfo(){
		this.info = new ArrayList<Client>();
	}
	public List<Client> getInfo() {
		return info;
	}

	public void setInfo(List<Client> info) {
		this.info = info;
	}
	
	public void addClient(Client client){
		synchronized (this) {
			info.add(client);
		}
	}
	public synchronized void deleteClient(String appName){
		synchronized (this) {
			for(int i = 0;i < info.size(); i++){
				if(info.get(i).getName().equals(appName)){
					info.remove(i);
					System.out.println(appName + "已经断开连接");
					break;
				}
			}
		}
	}
}
