package com.github.chenjianhua.springboot.mybatis.jpa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * @author chenjianhua
 * @date 2020-09-07 15:41:49
 */
@EnableSwagger2
@SpringBootApplication
@MapperScan("com.github.chenjianhua.springboot.mybatis.jpa.mapper")
public class Application {
    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
