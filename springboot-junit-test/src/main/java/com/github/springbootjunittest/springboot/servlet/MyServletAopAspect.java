package com.github.springbootjunittest.springboot.servlet;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author chenjianhua
 * @date 2021/3/31
 */
@Slf4j
@Aspect
@Component
public class MyServletAopAspect {

    @Before("execution(* com.github.springbootjunittest.springboot.servlet.ServletAopAdapterService.handleRequest(..))")
    public void before(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        log.info("servlet拦截," + method.getName());
    }
}
