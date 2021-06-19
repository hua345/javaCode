package com.github.chenjianhua.springboot.jdbc.param;

import com.github.chenjianhua.common.mybatisplus.vo.PageParam;
import com.github.chenjianhua.springboot.jdbc.mybatisplus.model.Book;
import lombok.Data;

/**
 * @author chenjianhua
 * @date 2021/4/27
 */
@Data
public class BookMybatisPlusParam extends PageParam<Book> {
    private String bookName;
}
