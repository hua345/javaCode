package com.github.springbootjunittest.designpattern.behavioralpattern.strategy;

import lombok.Getter;

/**
 * @author chenjianhua
 * @date 2021/5/23
 */
@Getter
public abstract class AbstractTestStrategy implements TestStrategy {
    protected TestStrategyEnum testStrategyEnum;

    public AbstractTestStrategy(TestStrategyEnum testStrategyEnum) {
        this.testStrategyEnum = testStrategyEnum;
        TestStrategyContext.registerStrategy(this);
    }

}
