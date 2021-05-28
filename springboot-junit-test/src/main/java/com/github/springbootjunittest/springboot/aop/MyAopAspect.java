package com.github.springbootjunittest.springboot.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
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
public class MyAopAspect {
    @Pointcut("@annotation(com.github.springbootjunittest.springboot.aop.MyAnnotation)")
    public void annotationPointCut() {
    }

    @Before("annotationPointCut()")
    public void beforeAnnotation(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        MyAnnotation action = method.getAnnotation(MyAnnotation.class);
        log.info("注解式拦截 before {}", action.name());
    }

    @After("annotationPointCut()")
    public void after(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        MyAnnotation action = method.getAnnotation(MyAnnotation.class);
        log.info("注解式拦截 after {}", action.name());
    }

    @Around("annotationPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        MyAnnotation action = method.getAnnotation(MyAnnotation.class);
        log.info("环绕通知前 {}", action.name());
        Object obj = (Object) joinPoint.proceed();
        log.info("环绕通知后 {}", action.name());
        return obj;
    }

    @Before("execution(* com.github.springbootjunittest.springboot.aop.DemoMethodService.add*(..))")
    public void before(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        log.info("方法规则式拦截," + method.getName());
    }
}
