package com.github.springbootjunittest.springboot.aop;

import org.springframework.stereotype.Service;

/**
 * @author chenjianhua
 * @date 2021/3/31
 */
@Service
public class DemoAnnotationService {
    @MyAnnotation(name = "注解式拦截的add操作")
    public void add() {
    }
}
