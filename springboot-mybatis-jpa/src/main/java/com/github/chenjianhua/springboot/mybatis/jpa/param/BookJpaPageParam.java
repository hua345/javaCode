package com.github.chenjianhua.springboot.mybatis.jpa.param;

import com.github.chenjianhua.common.jpa.vo.PageParam;
import com.github.chenjianhua.springboot.mybatis.jpa.model.Book;
import lombok.Data;

/**
 * @author chenjianhua
 * @date 2021/4/27
 */
@Data
public class BookJpaPageParam extends PageParam<Book> {
    private String bookName;
}
