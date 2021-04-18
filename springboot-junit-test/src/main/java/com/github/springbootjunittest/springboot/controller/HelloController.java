package com.github.springbootjunittest.springboot.controller;

import com.github.chenjianhua.common.json.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenjianhua
 * @date 2021/4/1
 */
@Slf4j
@RestController
public class HelloController {
    @GetMapping("/helloController")
    public String getHelloController(@RequestParam String name) {
        log.info("getHelloController param:{}", name);
        return "hello " + name;
    }

    @PostMapping("/helloController")
    public String postHelloController(@RequestBody HelloParam param) {
        log.info("postHelloController param:{}", JsonUtil.toJsonString(param));
        return "hello " + param.getName();
    }
}
