package com.github.springbootjunittest.designpattern.behavioralpattern.strategySpring;

import com.github.springbootjunittest.designpattern.behavioralpattern.strategy.TestStrategyEnum;
import lombok.Getter;

/**
 * @author chenjianhua
 * @date 2021/5/23
 */
@Getter
public abstract class AbstractTestAnnotationStrategy implements TestAnnotationStrategy {
    protected TestStrategyEnum testStrategyEnum;

    public AbstractTestAnnotationStrategy(TestStrategyEnum testStrategyEnum) {
        this.testStrategyEnum = testStrategyEnum;
    }

}
