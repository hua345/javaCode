package com.github.chenjianhua.rabbitmq.producer.param;

import lombok.Data;

/**
 * @author chenjianhua
 * @date 2021/6/26
 */
@Data
public class CreateBookOrderParam {
    /**
     * 图书名
     */
    private String bookName;

    /**
     * 交易数量
     */
    private Integer tradeNum;
}
