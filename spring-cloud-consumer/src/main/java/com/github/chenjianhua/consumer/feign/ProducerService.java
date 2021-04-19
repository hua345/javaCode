package com.github.chenjianhua.consumer.feign;


import com.github.chenjianhua.consumer.feign.hystrix.ProducerServiceHystrixFallbackService;
import com.github.common.resp.ResponseVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author chenjianhua
 * @date 2020/11/06
 */
@FeignClient(name = "spring-cloud-producer", fallbackFactory = ProducerServiceHystrixFallbackService.class)
public interface ProducerService {
    /**
     * 测试接口
     */
    @GetMapping("/hello")
    ResponseVO<String> hello(@RequestParam String name);
}

