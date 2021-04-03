package com.github.springbootjunittest.springboot.bean;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author chenjianhua
 * @date 2021/4/3
 */
@Slf4j
@Data
public class BookAnnotation {
    private String name;

    public BookAnnotation(String name) {
        this.name = name;
    }

    @PostConstruct
    public void initBook() {
        log.info("初始化{} bean之前执行", this.getClass().getSimpleName());
    }

    @PreDestroy
    public void destroyBook() {
        log.info("{} bean销毁之前执行", this.getClass().getSimpleName());
    }
}
