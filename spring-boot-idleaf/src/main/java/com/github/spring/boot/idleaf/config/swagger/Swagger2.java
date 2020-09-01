package com.github.spring.boot.idleaf.config.swagger;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author chenjianhua
 * @date 2020-09-01 16:46:26
 */
@Configuration
public class Swagger2 {
    @Bean
    public Docket createRestApi(@Value("${spring.profiles.active}") String env) {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.github.spring.boot.idleaf.controller"))
                .paths(StringUtils.equals("dev", env) ? PathSelectors.any() : PathSelectors.none())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("springboot利用swagger构建api文档")
                .description("简单优雅的restfun风格")
                .termsOfServiceUrl("https://github.com/")
                .version("1.0")
                .build();
    }
}
