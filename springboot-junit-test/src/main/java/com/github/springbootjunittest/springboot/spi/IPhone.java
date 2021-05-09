package com.github.springbootjunittest.springboot.spi;

/**
 * @author chenjianhua
 * @date 2021/5/8
 */
public class IPhone implements Phone{
    @Override
    public String getPhoneName() {
        return "苹果";
    }
}
