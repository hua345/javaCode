package com.github.id.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "id.leaf")
public class IdLeafAutoProperties {
    /**
     * 默认步长
     */
    private Integer step = 1000;
}
