package com.github.id.config;

import com.github.id.leaf.IdLeafRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConditionalOnClass({IdLeafAutoProperties.class})
@ConditionalOnWebApplication
@EnableConfigurationProperties({IdLeafAutoProperties.class})
public class IdLeafAutoConfiguration {
    @Autowired
    private IdLeafAutoProperties idLeafAutoProperties;

    @Bean
    public IdLeafRedisService idLeafRedisService() {
        IdLeafRedisService bookService = new IdLeafRedisService(idLeafAutoProperties);
        return bookService;
    }

}

