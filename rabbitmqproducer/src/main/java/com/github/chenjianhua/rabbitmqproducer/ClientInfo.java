package com.github.chenjianhua.rabbitmqproducer;

import java.util.List;

public class ClientInfo {
	private List<Client> info;

	public List<Client> getInfo() {
		return info;
	}

	public void setInfo(List<Client> info) {
		this.info = info;
	}
	
	public void addClient(Client client){
		info.add(client);
	}
	public void deleteClient(String appName){
		
	}
}
