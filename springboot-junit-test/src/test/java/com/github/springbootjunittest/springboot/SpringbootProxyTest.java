package com.github.springbootjunittest.springboot;

import com.github.springbootjunittest.springboot.proxy.HelloProxyService;
import com.github.springbootjunittest.springboot.proxy.HelloProxyServiceImpl;
import com.github.springbootjunittest.springboot.proxy.HelloJdkProxy;
import org.junit.jupiter.api.Test;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.test.util.AopTestUtils;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author chenjianhua
 * @date 2021/5/14
 */
public class SpringbootProxyTest {
    @Test
    public void jdkProxyTest() {
        HelloProxyServiceImpl helloProxyServiceImpl = new HelloProxyServiceImpl();
        HelloJdkProxy helloJdkProxy = new HelloJdkProxy(helloProxyServiceImpl);
        HelloProxyService helloProxyService = helloJdkProxy.createProxy();
        helloProxyService.helloWorld();
    }

    private static HelloProxyService getProxy(Object targetObj) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(targetObj);
        proxyFactory.addAdvice((MethodBeforeAdvice) (method, args1, target) -> {
            System.out.println("方法之前执行了~~~");
        });

        HelloProxyService helloService = (HelloProxyService) proxyFactory.getProxy();
        helloService.helloWorld();
        System.out.println(helloService.getClass().getName());
        return helloService;
    }

    @Test
    public void springProxyTest() {
        HelloProxyService helloProxyService = getProxy(new HelloProxyServiceImpl());
        // AopUtils.isAopProxy:是否是代理对象
        assertTrue(AopUtils.isAopProxy(helloProxyService));
        assertFalse(AopUtils.isJdkDynamicProxy(helloProxyService));
        assertTrue(AopUtils.isCglibProxy(helloProxyService));

        // 拿到目标对象
        System.out.println(AopUtils.getTargetClass(helloProxyService));
        Method method = ClassUtils.getMethod(HelloProxyServiceImpl.class, "helloWorld");
        System.out.println(AopUtils.selectInvocableMethod(method, HelloProxyServiceImpl.class));
        // 它是对ClassUtils.getMostSpecificMethod,增加了对代理对象的特殊处理。。。
        System.out.println(AopUtils.getMostSpecificMethod(method, HelloProxyService.class));
        assertEquals(AopProxyUtils.ultimateTargetClass(helloProxyService), HelloProxyServiceImpl.class);
        assertEquals(AopTestUtils.getTargetObject(helloProxyService).getClass(), HelloProxyServiceImpl.class);
        assertEquals(AopTestUtils.getUltimateTargetObject(helloProxyService).getClass(), HelloProxyServiceImpl.class);
    }
}
