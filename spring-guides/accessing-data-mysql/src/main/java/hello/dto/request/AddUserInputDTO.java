package hello.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author CHENJIANHUA001
 * @date 2019/03/18 15:28
 */
@Getter
@Setter
public class AddUserInputDTO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 名称
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;
}
