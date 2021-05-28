package com.github.springbootjunittest.designpattern.behavioralpattern.strategySpring;

import com.github.springbootjunittest.designpattern.behavioralpattern.strategy.TestStrategyEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenjianhua
 * @date 2021/5/23
 */
@Slf4j
@Component
public class TestAnnotationStrategyContext {
    private final Map<TestStrategyEnum, Class<AbstractTestAnnotationStrategy>> strategyClassMap = new ConcurrentHashMap<>(64);

    private static final Map<TestStrategyEnum, AbstractTestAnnotationStrategy> testStrategyMap = new ConcurrentHashMap<>();

    /**
     * 注册策略
     */
    public void registerStrategy(TestStrategyEnum testStrategyEnum, Class<AbstractTestAnnotationStrategy> strategyClass) {
        if (strategyClassMap.containsKey(testStrategyEnum)) {
            throw new RuntimeException("strategy type:" + testStrategyEnum + " exist");
        }
        strategyClassMap.put(testStrategyEnum, strategyClass);
    }

    public static AbstractTestAnnotationStrategy getTestStrategy(TestStrategyEnum testStrategyEnum) {
        AbstractTestAnnotationStrategy abstractTestAnnotationStrategy = testStrategyMap.get(testStrategyEnum);
        if (null == abstractTestAnnotationStrategy) {
            log.error("没有查询到策略:{}", testStrategyEnum);
            return null;
        }
        return abstractTestAnnotationStrategy;
    }

    public static void runAlgorithm(TestStrategyEnum testStrategyEnum) {
        AbstractTestAnnotationStrategy abstractTestAnnotationStrategy = testStrategyMap.get(testStrategyEnum);
        if (null == abstractTestAnnotationStrategy) {
            log.error("没有查询到策略:{}", testStrategyEnum);
            return;
        }
        abstractTestAnnotationStrategy.algorithm();
    }
}
