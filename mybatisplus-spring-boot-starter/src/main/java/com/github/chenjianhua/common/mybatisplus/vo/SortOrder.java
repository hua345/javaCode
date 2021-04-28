package com.github.chenjianhua.common.mybatisplus.vo;

import lombok.Data;

/**
 * @author chenjianhua
 * @date 2021/4/23
 */
@Data
public class SortOrder {
    /**
     * 字段名
     */
    private String sortName;
    /**
     * 排序方式
     */
    private Direction sortOrder;

    public static SortOrder of(String sortName, Direction direction) {
        SortOrder sortOrder = new SortOrder();
        sortOrder.setSortName(sortName);
        sortOrder.setSortOrder(direction);
        return sortOrder;
    }
}
