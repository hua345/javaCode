package com.github.chenjianhua.rabbitmq.producer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan
public class SpringbootRabbitmqProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootRabbitmqProducerApplication.class, args);
	}

}
