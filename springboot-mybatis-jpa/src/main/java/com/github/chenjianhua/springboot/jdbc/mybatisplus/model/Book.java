package com.github.chenjianhua.springboot.jdbc.mybatisplus.model;

import lombok.Data;

/**
 * @author chenjianhua
 * @date 2020-09-07 15:41:49
 */
@Data
public class Book extends AbstractLongModel {

    private String bookName;
}