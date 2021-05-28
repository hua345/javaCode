package com.github.springbootjunittest.springboot;

import com.github.springbootjunittest.springboot.validator.UploadDataModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;

import java.util.stream.Collectors;

/**
 * @author chenjianhua
 * @date 2021/5/20
 */
@SpringBootTest
public class SpringbootSmartValidatorTest {
    @Autowired
    private SmartValidator validator;

    @Test
    @DisplayName("springboot校验注解检查")
    protected void checkBindingResult() {
        UploadDataModel uploadDataModel = new UploadDataModel();
        uploadDataModel.setBookId(1L);
        BindingResult bindingResult = new BeanPropertyBindingResult(uploadDataModel, uploadDataModel.getClass().getSimpleName());
        if (null == validator) {
            return;
        }
        validator.validate(uploadDataModel, bindingResult);
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(";"));
            Assertions.assertEquals("图书名称不能为空", message);
        }
    }

    @Test
    @DisplayName("springboot校验注解检查")
    protected void testValidator() {
        UploadDataModel uploadDataModel = new UploadDataModel();
        uploadDataModel.setBookName("");
        uploadDataModel.setBookId(1L);
        BindingResult bindingResult = new BeanPropertyBindingResult(uploadDataModel, uploadDataModel.getClass().getSimpleName());
        if (null == validator) {
            return;
        }
        validator.validate(uploadDataModel, bindingResult);
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .sorted().collect(Collectors.joining(";"));
            Assertions.assertEquals("图书名称不能为空;图书名称长度需要大于2", message);
        }
    }
}
