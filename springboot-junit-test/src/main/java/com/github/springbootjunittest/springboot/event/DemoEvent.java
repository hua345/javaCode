package com.github.springbootjunittest.springboot.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author chenjianhua
 * @date 2021/4/4
 */
public class DemoEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    private String message;

    public DemoEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
