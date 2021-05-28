package com.github.springbootjunittest.designpattern.behavioralpattern.strategySpring;

import com.github.springbootjunittest.designpattern.behavioralpattern.strategy.TestStrategyEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author chenjianhua
 * @date 2021/5/23
 */
@Slf4j
@MyStrategyAnnotation(name = "A")
@Component
public class TestConcreteAnnotationStrategyA extends AbstractTestAnnotationStrategy {

    public TestConcreteAnnotationStrategyA() {
        super(TestStrategyEnum.ALGORITHM_A);
    }

    @Override
    public void algorithm() {
        log.info("算法A");
    }
}
