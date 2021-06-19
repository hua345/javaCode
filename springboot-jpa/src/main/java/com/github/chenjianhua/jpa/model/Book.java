package com.github.chenjianhua.jpa.model;

import com.github.chenjianhua.common.leafjpa.model.AbstractLongModel;
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