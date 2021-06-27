package com.github.chenjianhua.rocketmq.producer.model;

import com.github.chenjianhua.common.mybatisplus.model.AbstractLongModel;
import lombok.Data;

/**
 * @author chenjianhua
 * @date 2021/6/26
 */
@Data
public class BookOrder extends AbstractLongModel {
    /**
     * 订单号
     */
    private String orderNumber;

    /**
     * 图书名
     */
    private String bookName;

    /**
     * 交易数量
     */
    private Integer tradeNum;
}