package com.github.common.enums;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author chenjianhua
 * @date 2020-09-01 16:46:26
 */
@Configuration
public class EnumConverterFactoryConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new StringToEnumConverterFactory());
        registry.addConverterFactory(new IntegerToEnumConverterFactory());
    }
}
