package hello.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author CHENJIANHUA001
 * @date 2019/03/18 15:28
 */
@Getter
@Setter
public class AddUserOutputDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 添加用户结果
     */
    private String status;
}
