package com.github.chenjianhua.springcloudsentinel;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenjianhua
 * @date 2021/4/30
 */
@Slf4j
public class SentinelTest {
    private static void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("HelloWorld");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 20.
        rule.setCount(10);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

    private static AtomicInteger count = new AtomicInteger();

    @SentinelResource("HelloWorld")
    public void helloWorld() {
        // 资源中的逻辑
        log.info("hello world:{}", count);
        count.incrementAndGet();
    }


    @Test
    public void SentinelAnnotationTest() {
        // 配置规则.
        initFlowRules();
        SentinelTest sentinelTest = new SentinelTest();
        while (true) {
            sentinelTest.helloWorld();
        }
    }

    @Test
    public void SentinelTest() {
        // 配置规则.
        initFlowRules();
        while (true) {
            // 1.5.0 版本开始可以直接利用 try-with-resources 特性
            try (Entry entry = SphU.entry("HelloWorld")) {
                // 被保护的逻辑
                log.info("hello world:{}", count);
                count.incrementAndGet();
            } catch (BlockException ex) {
                // 处理被流控的逻辑
//                log.info("blocked!");
            }
        }
    }
}
