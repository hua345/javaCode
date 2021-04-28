package com.github.chenjianhua.springboot.jdbc.param;

import com.github.common.enums.FruitEnum;
import com.github.common.enums.SexEnum;
import lombok.Data;

/**
 * @author chenjianhua
 * @date 2020/12/1
 */
@Data
public class TestEnumParam {

    private FruitEnum fruit;

    private SexEnum sex;
}
