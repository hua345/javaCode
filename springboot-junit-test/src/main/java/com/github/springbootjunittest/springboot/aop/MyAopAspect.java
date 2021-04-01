package com.github.springbootjunittest.springboot.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Method;

/**
 * @author chenjianhua
 * @date 2021/3/31
 */
@Slf4j
@Aspect
@Component
public class MyAopAspect {
    @Pointcut("@annotation(com.github.springbootjunittest.springboot.aop.MyAnnotation)")
    public void annotationPointCut() {
    }

    @After("annotationPointCut()")
    public void after(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        MyAnnotation action = method.getAnnotation(MyAnnotation.class);
        log.info("注解式拦截 " + action.name());
    }

    @Before("execution(* com.github.springbootjunittest.springboot.aop.DemoMethodService.add*(..))")
    public void before(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        log.info("方法规则式拦截," + method.getName());
    }
}
