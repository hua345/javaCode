package com.github.chenjianhua.springcloudsentinel.feign.hystrix;

import com.github.chenjianhua.springcloudsentinel.feign.ProducerService;
import com.github.common.resp.ResponseVO;
import com.github.common.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author chenjianhua
 * @date 2021/3/12
 */
@Slf4j
@Component
public class ProducerServiceFallbackService implements ProducerService {

    @Override
    public ResponseVO<String> hello(String name) {
        log.error("调用生产者失败-param:{}", name);
        return ResponseUtil.fail();
    }
}