package com.github.chenjianhua.model;
import java.io.Serializable;

public class MUser implements Serializable{
	private static final long serivalVersionUID = 1L;
	private String id;
	private String name;
	private int age;
	public String getName() {
		return name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public String toString(){
		return "User [name=" + name + ",age=" + age + "]";
	}
}
