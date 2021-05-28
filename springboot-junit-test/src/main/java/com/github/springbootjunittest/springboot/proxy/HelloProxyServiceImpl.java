package com.github.springbootjunittest.springboot.proxy;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenjianhua
 * @date 2021/5/14
 */
@Slf4j
public class HelloProxyServiceImpl implements HelloProxyService {
    @Override
    public void helloWorld() {
        log.info("hello world");
    }
}
