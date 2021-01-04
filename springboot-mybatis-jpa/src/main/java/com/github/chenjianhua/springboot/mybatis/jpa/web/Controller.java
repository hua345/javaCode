package com.github.chenjianhua.springboot.mybatis.jpa.web;

import com.github.chenjianhua.springboot.mybatis.jpa.param.TestEnumParam;
import com.github.common.enums.SexEnum;
import com.github.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenjianhua
 * @date 2020/12/1
 */
@Slf4j
@RestController
public class Controller {
    @PostMapping("/fruit")
    public TestEnumParam testEnum(@RequestBody TestEnumParam param) {
        log.info("param :{}", JsonUtil.toJSONString(param));
        return param;
    }
    @PostMapping("/fruit2")
    public SexEnum testEnum2(@RequestParam SexEnum param) {
        log.info("param :{}", JsonUtil.toJSONString(param));
        return param;
    }
}