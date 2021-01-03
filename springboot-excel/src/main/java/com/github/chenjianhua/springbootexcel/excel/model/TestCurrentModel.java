package com.github.chenjianhua.springbootexcel.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chenjianhua
 * @date 2020/12/31
 */
@Data
public class TestCurrentModel {
    /**
     * 书名
     */
    @ExcelProperty(value = "书名", index = 0)
    private String bookName;
    /**
     * 价格
     */
    @ExcelProperty(value = "价格", index = 1)
    private BigDecimal bookPrice;
}
