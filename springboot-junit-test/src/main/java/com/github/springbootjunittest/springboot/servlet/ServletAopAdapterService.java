package com.github.springbootjunittest.springboot.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chenjianhua
 * @date 2021/4/1
 */
@Slf4j
@Component
public class ServletAopAdapterService {
    public void handleRequest() {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("servlet aop拦截");
    }
}
