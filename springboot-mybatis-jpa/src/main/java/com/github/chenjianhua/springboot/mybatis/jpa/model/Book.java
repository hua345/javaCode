package com.github.chenjianhua.springboot.mybatis.jpa.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author chenjianhua
 * @date 2020-09-07 15:41:49
 */
@Data
@Entity
@Table
public class Book extends AbstractLongModel {

    private String bookName;
}