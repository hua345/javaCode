package com.github.springbootjunittest.springboot.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author chenjianhua
 * @date 2021/5/14
 */
@Slf4j
public class HelloJdkProxy implements InvocationHandler {

    /**
     * 要被代理的目标对象
     */
    private HelloProxyServiceImpl target;

    public HelloJdkProxy(HelloProxyServiceImpl target) {
        this.target = target;
    }

    /**
     * 创建代理类
     *
     * @return
     */
    public HelloProxyService createProxy() {
        return (HelloProxyService) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    /**
     * 调用被代理类(目标对象)的任意方法都会触发invoke方法
     *
     * @param proxy  代理类
     * @param method 被代理类的方法
     * @param args   被代理类的方法参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //过滤不需要该业务的方法
        if ("helloWorld".equals(method.getName())) {
            log.info("代理前日志");
            //调用目标对象的方法
            Object result = method.invoke(target, args);
            log.info("代理后日志");
            return result;
        }
        //如果不需要增强直接执行原方法
        return method.invoke(target, args);
    }
}
