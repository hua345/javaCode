package com.github.chenjianhua.hellospringbootstarter.service;

import com.github.chenjianhua.hellospringbootstarter.config.HelloProperties;

/**
 * @author chenjianhua
 * @date 2021/3/12
 */
public class HelloService {
    private String message;

    public HelloService(HelloProperties helloProperties) {
        this.message = helloProperties.getMessage();
    }

    public String hello() {
        StringBuilder sb = new StringBuilder();
        sb.append("hello ").append(message);
        return sb.toString();
    }
}
