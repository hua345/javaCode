package com.github.chenjianhua.common.id.leaf;

import lombok.Data;

/**
 * @author chenjianhua
 * @date 2020-09-01 16:46:26
 */
@Data
public class LeafAlloc {

    private String bizTag;

    private Long maxId;

    private Integer step;
}