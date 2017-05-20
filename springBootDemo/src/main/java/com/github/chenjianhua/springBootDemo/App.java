package com.github.chenjianhua.springBootDemo;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

/**
 * Hello world!
 *
 */
public class App 
{
	@RequestMapping("/")
	String home(){
		return "Hello World";
	}
    public static void main( String[] args ) throws Exception
    {
        SpringApplication.run(App.class, args);
    }
}
