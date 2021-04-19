package com.github.chenjianhua.consumer.feign.hystrix;

import com.github.chenjianhua.consumer.feign.ProducerService;
import com.github.common.resp.ResponseVO;
import com.github.common.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author chenjianhua
 * @date 2021/3/12
 */
@Slf4j
@Component
public class ProducerServiceHystrixFallbackService implements FallbackFactory<ProducerService> {

    @Override
    @SuppressWarnings("unchecked")
    public ProducerService create(Throwable cause) {
        return new ProducerService() {
            @Override
            public ResponseVO<String> hello(@RequestParam String name) {
                log.error("调用生产者失败-param:{} {} {}", name, cause.getMessage(), cause.getStackTrace());
                return ResponseUtil.fail();
            }
        };
    }
}