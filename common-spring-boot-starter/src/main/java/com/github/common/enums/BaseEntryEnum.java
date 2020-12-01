package com.github.common.enums;

import lombok.Data;

/**
 * @author chenjianhua
 * @date 2020-09-01 16:46:26
 */
@Data
public class BaseEntryEnum<T> {

    T type;

    String desc;
}
