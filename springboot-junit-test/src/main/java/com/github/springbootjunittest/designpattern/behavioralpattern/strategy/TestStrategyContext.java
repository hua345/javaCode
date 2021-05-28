package com.github.springbootjunittest.designpattern.behavioralpattern.strategy;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenjianhua
 * @date 2021/5/23
 */
@Slf4j
public class TestStrategyContext {
    private static final Map<TestStrategyEnum, AbstractTestStrategy> testStrategyMap = new ConcurrentHashMap<>();

    /**
     * 注册导出策略
     *
     * @param executeStrategy 策略实体
     */
    public static void registerStrategy(AbstractTestStrategy executeStrategy) {
        TestStrategyEnum excelExportType = executeStrategy.getTestStrategyEnum();
        log.info("注册策略:{}, ", excelExportType);
        if (excelExportType != null) {
            testStrategyMap.put(excelExportType, executeStrategy);
        }
    }

    public static AbstractTestStrategy getTestStrategy(TestStrategyEnum testStrategyEnum) {
        AbstractTestStrategy abstractTestStrategy = testStrategyMap.get(testStrategyEnum);
        if (null == abstractTestStrategy) {
            log.error("没有查询到策略:{}", testStrategyEnum);
            return null;
        }
        return abstractTestStrategy;
    }

    public static void runAlgorithm(TestStrategyEnum testStrategyEnum) {
        AbstractTestStrategy abstractTestStrategy = testStrategyMap.get(testStrategyEnum);
        if (null == abstractTestStrategy) {
            log.error("没有查询到策略:{}", testStrategyEnum);
            return;
        }
        abstractTestStrategy.algorithm();
    }
}
