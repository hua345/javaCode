package com.github.springbootjunittest.designpattern.behavioralpattern.template;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author chenjianhua
 * @date 2021/4/3
 */
class LearnDesignPatternTest {

    @Test
    public void learnTemplateTest(){
        LearnDesignPattern learnDesignPattern = new LearnDesignPattern();
        learnDesignPattern.doLearn();
    }
}