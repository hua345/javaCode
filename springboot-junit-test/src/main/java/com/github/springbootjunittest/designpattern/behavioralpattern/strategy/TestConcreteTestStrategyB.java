package com.github.springbootjunittest.designpattern.behavioralpattern.strategy;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenjianhua
 * @date 2021/5/23
 */
@Slf4j
public class TestConcreteTestStrategyB extends AbstractTestStrategy {
    public TestConcreteTestStrategyB() {
        super(TestStrategyEnum.ALGORITHM_B);
    }

    @Override
    public void algorithm() {
        log.info("算法B");
    }
}
