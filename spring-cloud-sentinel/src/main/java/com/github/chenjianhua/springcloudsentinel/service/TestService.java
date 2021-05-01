package com.github.chenjianhua.springcloudsentinel.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.github.common.resp.ResponseVO;
import com.github.common.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author chenjianhua
 * @date 2021/4/30
 */
@Slf4j
@Service
public class TestService {
    /**
     * Sentinel测试
     */
    @SentinelResource(value = "hello", blockHandler = "helloExceptionHandler", fallback = "helloFallback")
    public ResponseVO<String> sayHello(String name) {
        return ResponseUtil.ok(String.format("hello %s", name));
    }

    /**
     * Fallback 函数，函数签名与原函数一致或加一个 Throwable 类型的参数.
     */
    public ResponseVO<String> helloFallback(String name) {
        return ResponseUtil.ok(String.format("hello %s fallback by sentinel", name));
    }

    /**
     * Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
     */
    public ResponseVO<String> helloExceptionHandler(String name, BlockException ex) {
        // Do some log here.
        log.info("BlockException:{}", ex.getMessage());
        return ResponseUtil.fail();
    }
}
