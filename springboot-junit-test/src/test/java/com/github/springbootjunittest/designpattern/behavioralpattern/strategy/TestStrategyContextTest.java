package com.github.springbootjunittest.designpattern.behavioralpattern.strategy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author chenjianhua
 * @date 2021/5/23
 */
class TestStrategyContextTest {

    @Test
    void runAlgorithm() {
        TestConcreteTestStrategyA testConcreteTestStrategyA = new TestConcreteTestStrategyA();
        TestConcreteTestStrategyB testConcreteTestStrategyB = new TestConcreteTestStrategyB();
        Assertions.assertNotNull(TestStrategyContext.getTestStrategy(TestStrategyEnum.ALGORITHM_A));
        Assertions.assertNotNull(TestStrategyContext.getTestStrategy(TestStrategyEnum.ALGORITHM_B));
        Assertions.assertEquals(TestStrategyEnum.ALGORITHM_A, TestStrategyContext.getTestStrategy(TestStrategyEnum.ALGORITHM_A).getTestStrategyEnum());
        Assertions.assertEquals(TestStrategyEnum.ALGORITHM_B, TestStrategyContext.getTestStrategy(TestStrategyEnum.ALGORITHM_B).getTestStrategyEnum());

        TestStrategyContext.runAlgorithm(TestStrategyEnum.ALGORITHM_A);
    }
}