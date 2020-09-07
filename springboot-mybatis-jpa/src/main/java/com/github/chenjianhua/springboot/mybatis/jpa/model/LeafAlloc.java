package com.github.chenjianhua.springboot.mybatis.jpa.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author chenjianhua
 * @date 2020-09-07 15:41:49
 */
@Getter
@Setter
public class LeafAlloc {

    private String bizTag;

    private Long maxId;

    private Integer step;

    private String description;

    private Date updateTime;

}