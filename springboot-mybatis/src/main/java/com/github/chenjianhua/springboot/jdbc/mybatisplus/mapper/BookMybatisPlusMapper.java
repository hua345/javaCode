package com.github.chenjianhua.springboot.jdbc.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.chenjianhua.springboot.jdbc.mybatisplus.model.Book;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author chenjianhua
 * @date 2021/4/28
 */
@Mapper
public interface BookMybatisPlusMapper extends BaseMapper<Book> {
    IPage<Book> mybatisPlusPage(IPage<?> iPage, String bookName);
}
