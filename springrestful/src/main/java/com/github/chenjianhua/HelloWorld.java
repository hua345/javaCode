package com.github.chenjianhua;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import com.github.chenjianhua.Person;
@Controller
public class HelloWorld {
	private static final Logger Log = Logger.getLogger(HelloWorld.class);
	@RequestMapping(value = "/hello", produces = "text/plain;charset=UTF-8")
	public @ResponseBody
	String hello(){
		return "你好!hello";
	}
	
	@RequestMapping(value = "/say/{msg}",
			produces = "text/plain;charset=UTF-8")
	public @ResponseBody
	String say(@PathVariable(value = "msg") String msg){
		return "{\"msg\":\"you say:'" + msg + "'\"}";
	}
	
	@RequestMapping(value = "/user/{id}", 
			produces = "application/json;charset=UTF-8",
			method = RequestMethod.GET)
	public @ResponseBody
	Person getPerson(@PathVariable(value = "id") String id){
		Log.info("获取人员信息id=" + id);
		Person person = new Person();
		person.setId(id);
		person.setName("fangfang");
		person.setAge(22);
		return person;
	}
}