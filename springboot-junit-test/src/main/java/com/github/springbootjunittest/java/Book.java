package com.github.springbootjunittest.java;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chenjianhua
 * @date 2021/5/19
 */
@Data
public class Book {
    private Long id;
    private String name;
    private BigDecimal price;

    public Book(Long id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    private Book() {
    }
}