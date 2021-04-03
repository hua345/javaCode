package com.github.springbootjunittest.designpattern.behavioralpattern.template;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenjianhua
 * @date 2021/4/3
 */
@Slf4j
public class LearnDesignPattern extends LearnTemplate {

    public LearnDesignPattern() {
        this.learnContent = "设计模式";
    }

    @Override
    public void howLearn() {
        log.info("看一些设计模式的书，练习设计模式的使用");
    }
}
