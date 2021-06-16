package com.github.springbootjunittest;

import com.github.springbootjunittest.springboot.beanscan.EnableMyScanAnnotation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@EnableMyScanAnnotation()
@SpringBootApplication
public class SpringbootJunitTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJunitTestApplication.class, args);
	}
}
