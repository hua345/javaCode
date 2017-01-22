package com.github.chenjianhua;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorld {
	@RequestMapping("/helloworld")
	public String hello(){
		System.out.println("helloworld");
		return "success";
	}
}
