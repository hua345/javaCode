package com.github.chenjianhua.hellospringbootstarter.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "hello")
public class HelloProperties {
    /**
     * 默认步长
     */
    private String message = "stater";
}
