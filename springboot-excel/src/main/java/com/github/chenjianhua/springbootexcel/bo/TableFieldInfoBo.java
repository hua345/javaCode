package com.github.chenjianhua.springbootexcel.bo;

import lombok.Data;

/**
 * @author chenjianhua
 * @date 2021/3/10
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
     * 显示状态
     */
    private Boolean displayStatus;
    /**
     * 显示次序
     */
    private Integer index;
}
