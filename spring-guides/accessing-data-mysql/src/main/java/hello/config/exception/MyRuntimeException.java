package hello.config.exception;

import hello.common.ResponseVO;
import hello.common.ResponseStatusEnum;
import lombok.Getter;

/**
 * @author CHENJIANHUA001
 * @date 2019/03/19 12:25
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
