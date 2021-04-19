package com.github.chenjianhua.producer.controller;

import com.github.chenjianhua.common.json.util.JsonUtil;
import com.github.chenjianhua.producer.vo.HelloParam;
import com.github.common.resp.ResponseVO;
import com.github.common.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenjianhua
 * @date 2021/3/12
 */
@Slf4j
@RestController
public class HelloController {

    @GetMapping("/hello")
    public ResponseVO<String> index(@RequestParam String name) {
        log.info("get hello param:{}", name);
        StringBuilder sb = new StringBuilder();
        sb.append("hello ").append(name);
        return ResponseUtil.ok(sb.toString());
    }

    @PostMapping("/hello")
    public ResponseVO<String> postHello(@RequestBody HelloParam param) {
        log.info("post hello param:{}", JsonUtil.toJsonString(param));
        StringBuilder sb = new StringBuilder();
        sb.append("hello ").append(param.getName());
        return ResponseUtil.ok(sb.toString());
    }
}