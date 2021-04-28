package com.github.chenjianhua.springboot.jdbc.mybatisplus.service;

import com.github.chenjianhua.common.mybatisplus.support.AbstractService;
import com.github.chenjianhua.common.mybatisplus.vo.PageVo;
import com.github.chenjianhua.springboot.jdbc.mybatisplus.model.Book;
import com.github.chenjianhua.springboot.jdbc.mybatisplus.mapper.BookMybatisPlusMapper;
import com.github.chenjianhua.springboot.jdbc.param.BookMybatisPlusParam;
import org.springframework.stereotype.Service;

/**
 * @author chenjianhua
 * @date 2021/4/28
 */
@Service
public class BookMybatisPlusService extends AbstractService<BookMybatisPlusMapper, Book> {
    public PageVo<Book> mybatisPlusPage(BookMybatisPlusParam param) {
        return convertPage(baseMapper.mybatisPlusPage(param.getPageable(), param.getBookName()));
    }
}
