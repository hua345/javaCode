package com.github.springbootjunittest.springboot.beanscan;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author chenjianhua
 * @date 2021/5/7
 */
@Configuration
@Import(MapperAutoConfigureRegistrar.class)
public class MyScanAutoConfig {
}
