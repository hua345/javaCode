package hello.config.exception;

import hello.common.ResponseModel;
import hello.common.ResultCodeEnum;
import lombok.Getter;

/**
 * @author CHENJIANHUA001
 * @date 2019/03/19 12:25
 */
@Getter
public class MyRuntimeException extends RuntimeException {

    private String code;
    public MyRuntimeException(String code, String message){
        super(message);
        this.code = code;
    }
    public MyRuntimeException(ResultCodeEnum resultCodeEnum){
        super(resultCodeEnum.getErrorMsg());
        this.code = resultCodeEnum.getErrorCode();
    }
    public ResponseModel getResponseResult(){
        return ResponseModel.result(this.code, this.getMessage());
    }
}
