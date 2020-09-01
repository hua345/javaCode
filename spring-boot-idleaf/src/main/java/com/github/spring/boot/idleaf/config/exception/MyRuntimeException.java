package com.github.spring.boot.idleaf.config.exception;

import com.github.spring.boot.idleaf.common.ResponseVO;
import com.github.spring.boot.idleaf.common.ResponseStatusEnum;
import lombok.Getter;

/**
 * @author chenjianhua
 * @date 2020-09-01 16:46:26
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