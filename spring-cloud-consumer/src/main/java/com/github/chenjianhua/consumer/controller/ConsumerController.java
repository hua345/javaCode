package com.github.chenjianhua.consumer.controller;

import com.github.chenjianhua.common.json.util.JsonUtil;
import com.github.chenjianhua.consumer.feign.ProducerService;
import com.github.chenjianhua.consumer.service.RestTemplateService;
import com.github.chenjianhua.consumer.vo.HelloParam;
import com.github.common.resp.ResponseVO;
import com.github.common.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenjianhua
 * @date 2021/3/12
 */
@RestController
public class ConsumerController {

    @Autowired
    ProducerService producerService;

    @Autowired
    RestTemplateService restTemplateService;

    @RequestMapping("/hello/{name}")
    public ResponseVO<String> index(@PathVariable("name") String name) {
        return producerService.hello(name);
    }

    @RequestMapping("/helloByServerName/{name}")
    public ResponseVO<String> helloByServerName(@PathVariable("name") String name) {
        HelloParam helloParam = new HelloParam();
        helloParam.setName(name);
        ResponseEntity<String> resp = restTemplateService.postJsonByServerName("spring-cloud-producer", "/postHello", JsonUtil.toJsonString(helloParam));
        return ResponseUtil.ok(resp.getBody());
    }
}