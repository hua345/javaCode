package com.github.chenjianhua.springboot.mybatis.jpa.config.exception;

import com.github.chenjianhua.springboot.mybatis.jpa.common.ResponseVO;
import com.github.chenjianhua.springboot.mybatis.jpa.common.ResponseStatusEnum;
import lombok.Getter;

/**
 * @author chenjianhua
 * @date 2020-09-07 15:41:49
 */
@Getter
public class MyRuntimeException extends RuntimeException {

    private Integer code;

    public MyRuntimeException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public MyRuntimeException(ResponseStatusEnum responseStatusEnum) {
        super(responseStatusEnum.getI18nKey());
        this.code = responseStatusEnum.getErrorCode();
    }

    public ResponseVO getResponseResult() {
        return new ResponseVO(this.code, this.getMessage());
    }
}