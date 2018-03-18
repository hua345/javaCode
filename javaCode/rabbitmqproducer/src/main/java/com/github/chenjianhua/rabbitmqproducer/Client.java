package com.github.chenjianhua.rabbitmqproducer;

public class Client {
	private String ID;
	private String name;
	private String register_date;
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRegister_date() {
		return register_date;
	}
	public void setRegister_date(String register_date) {
		this.register_date = register_date;
	}
	public String toString(){
		return "App Infomation: ID=" + 
			this.ID + ", name=" + 
			this.name + ", register_date=" + 
			this.register_date;
	}
}
