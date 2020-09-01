package com.github.spring.boot.idleaf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author chenjianhua
 * @date 2020-09-01 16:46:26
 */
@EnableSwagger2
@SpringBootApplication
@MapperScan("com.github.spring.boot.idleaf.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
