package com.github.springbootjunittest.springboot.validator;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author chenjianhua
 * @date 2021/3/25
 */
@Getter
@Setter
public class UploadDataModel {
    @NotNull(message = "图书Id不能为空")
    @MyValidatorAnnotation(name = "图书Id")
    private Long bookId;

    @NotEmpty(message = "图书名称不能为空")
    @Length(min = 2, message = "图书名称长度需要大于2")
    @MyValidatorAnnotation(name = "图书名称")
    private String bookName;

    @MyValidatorAnnotation(name = "图书价格")
    private BigDecimal bookPrice;
}
