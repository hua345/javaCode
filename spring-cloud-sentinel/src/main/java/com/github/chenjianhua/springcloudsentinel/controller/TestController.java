package com.github.chenjianhua.springcloudsentinel.controller;

import com.github.chenjianhua.springcloudsentinel.feign.ProducerService;
import com.github.chenjianhua.springcloudsentinel.service.TestService;
import com.github.common.resp.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenjianhua
 * @date 2021/5/1
 */
@Slf4j
@RestController
public class TestController {
    @Autowired
    private TestService service;

    @Autowired
    private ProducerService producerService;

    @GetMapping(value = "/hello/{name}")
    public ResponseVO<String> apiHello(@PathVariable String name) {
        return service.sayHello(name);
    }

    @GetMapping(value = "/helloFeign/{name}")
    public ResponseVO<String> helloFeign(@PathVariable String name) {
        return producerService.hello(name);
    }


}
