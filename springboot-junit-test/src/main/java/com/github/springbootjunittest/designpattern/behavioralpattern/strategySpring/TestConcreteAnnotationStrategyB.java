package com.github.springbootjunittest.designpattern.behavioralpattern.strategySpring;

import com.github.springbootjunittest.designpattern.behavioralpattern.strategy.TestStrategyEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author chenjianhua
 * @date 2021/5/23
 */
@Slf4j
@MyStrategyAnnotation(name = "B")
@Component
public class TestConcreteAnnotationStrategyB extends AbstractTestAnnotationStrategy {
    public TestConcreteAnnotationStrategyB() {
        super(TestStrategyEnum.ALGORITHM_B);
    }

    @Override
    public void algorithm() {
        log.info("算法B");
    }
}
