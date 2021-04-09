package com.github.springbootjunittest.java.reflect;

import lombok.Data;

/**
 * @author chenjianhua
 * @date 2021/3/22
 */
@Data
public class TableFieldInfoBo {
    /**
     * 字段名称
     */
    private String fieldCode;
    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * fieldCode对应的字段
     */
    private String name;
}
