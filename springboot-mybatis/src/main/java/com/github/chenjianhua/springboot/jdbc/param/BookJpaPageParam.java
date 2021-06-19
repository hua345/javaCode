package com.github.chenjianhua.springboot.jdbc.param;

import com.github.chenjianhua.common.mybatisplus.vo.PageParam;
import com.github.chenjianhua.springboot.jdbc.mybatis.model.Book;
import lombok.Data;

/**
 * @author chenjianhua
 * @date 2021/4/27
 */
@Data
public class BookJpaPageParam extends PageParam<Book> {
    private String bookName;
}
