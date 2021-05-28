package com.github.springbootjunittest.designpattern.behavioralpattern.strategy;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenjianhua
 * @date 2021/5/23
 */
@Slf4j
public class TestConcreteTestStrategyA extends AbstractTestStrategy {

    public TestConcreteTestStrategyA() {
        super(TestStrategyEnum.ALGORITHM_A);
    }

    @Override
    public void algorithm() {
        log.info("算法A");
    }
}
