package com.github.id.leaf;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LeafAlloc {

    private String bizTag;

    private Long maxId;

    private Integer step;

    private String description;
}