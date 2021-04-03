package com.github.springbootjunittest.springboot.bean;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chenjianhua
 * @date 2021/4/3
 */
@Slf4j
@Data
public class BookConfigInitMethod {
    private String name;

    public BookConfigInitMethod(String name) {
        this.name = name;
    }

    public void initBook() {
        log.info("初始化{} bean之前执行", this.getClass().getSimpleName());
    }

    public void destroyBook() {
        log.info("{} bean销毁之前执行", this.getClass().getSimpleName());
    }
}
