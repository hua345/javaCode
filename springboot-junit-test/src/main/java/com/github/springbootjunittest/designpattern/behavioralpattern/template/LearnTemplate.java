package com.github.springbootjunittest.designpattern.behavioralpattern.template;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenjianhua
 * @date 2021/4/3
 */
@Slf4j
public abstract class LearnTemplate {

    /**
     * 学习内容
     */
    protected String learnContent;

    protected void doLearn() {
        howLearn();
        takeTimeToLearn();
        writeSummaryDoc();
        review();
    }

    /**
     * 怎么去学习该内容
     */
    public abstract void howLearn();

    /**
     * 花时间学习
     */
    protected void takeTimeToLearn() {
        log.info("花时间学习{}", learnContent);
    }

    /**
     * 复习
     */
    protected void review() {
        log.info("复习{}", learnContent);
    }

    /**
     * 写总结文档
     */
    protected void writeSummaryDoc() {
        log.info("进行{}总结", learnContent);
    }
}
