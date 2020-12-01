package com.github.chenjianhua.springboot.mybatis.jpa.param;

import com.github.chenjianhua.springboot.mybatis.jpa.enums.SexEnum;
import com.github.chenjianhua.springboot.mybatis.jpa.enums.FruitEnum;
import lombok.Data;

/**
 * @author chenjianhua
 * @date 2020/12/1
 */
@Data
public class TestEnumParam {

    private FruitEnum fruit;

    private SexEnum fruit2;
}
