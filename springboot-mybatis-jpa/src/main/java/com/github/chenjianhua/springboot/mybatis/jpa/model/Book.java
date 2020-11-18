package com.github.chenjianhua.springboot.mybatis.jpa.model;

import com.github.id.model.AbstractLeafModel;
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
public class Book extends AbstractLeafModel {

    private String bookName;
}