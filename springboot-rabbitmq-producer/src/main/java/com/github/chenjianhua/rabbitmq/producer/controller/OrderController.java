package com.github.chenjianhua.rabbitmq.producer.controller;

import com.github.chenjianhua.rabbitmq.producer.param.CreateBookOrderParam;
import com.github.chenjianhua.rabbitmq.producer.service.BookOrderService;
import com.github.common.resp.ResponseVO;
import com.github.common.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenjianhua
 * @date 2021/6/26
 */
@RestController
public class OrderController {
    @Autowired
    private BookOrderService bookOrderService;

    @PostMapping("/createBookOrder")
    public ResponseVO createBookOrder(@RequestBody CreateBookOrderParam param) {
        bookOrderService.createBookOrder(param);
        return ResponseUtil.ok();
    }

    @PostMapping("/mysqlTimeOut")
    public ResponseVO mysqlTimeOut() {
        bookOrderService.mysqlTimeOut();
        return ResponseUtil.ok();
    }
}
