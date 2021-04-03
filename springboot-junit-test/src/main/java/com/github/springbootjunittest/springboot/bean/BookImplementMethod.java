package com.github.springbootjunittest.springboot.bean;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author chenjianhua
 * @date 2021/4/3
 */
@Slf4j
@Data
public class BookImplementMethod implements InitializingBean, DisposableBean {
    private String name;

    public BookImplementMethod(String name) {
        this.name = name;
    }

    @Override
    public void afterPropertiesSet() {
        log.info("初始化{} bean之前执行", this.getClass().getSimpleName());
    }

    @Override
    public void destroy() {
        log.info("{} bean销毁之前执行", this.getClass().getSimpleName());
    }
}
