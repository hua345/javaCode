package com.github.spring.boot.idleaf.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author chenjianhua
 * @date 2020-09-01 16:46:26
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