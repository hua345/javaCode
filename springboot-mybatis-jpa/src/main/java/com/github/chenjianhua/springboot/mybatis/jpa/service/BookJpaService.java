package com.github.chenjianhua.springboot.mybatis.jpa.service;

import com.github.chenjianhua.common.jpa.support.JdbcQuery;
import com.github.chenjianhua.common.jpa.support.JdbcTemplateService;
import com.github.chenjianhua.springboot.mybatis.jpa.mapper.BookMapper;
import com.github.chenjianhua.springboot.mybatis.jpa.model.Book;
import com.github.chenjianhua.springboot.mybatis.jpa.param.BookJpaPageParam;
import com.github.chenjianhua.springboot.mybatis.jpa.repository.BookRepository;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author chenjianhua
 * @date 2020/9/7
 */
@Slf4j
@Service
public class BookJpaService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private JdbcTemplateService jdbcTemplateService;

    public void jpaSave(Book book) {
        bookRepository.save(book);
    }

    public Optional<Book> jpaFindById(Long id) {
        return bookRepository.findById(id);
    }

    public Page<Book> jpaPageNativeQuery(String bookName, Pageable pageable) {
        Page<Book> bookPage = bookRepository.jpaPageBookByNameSQL(bookName, pageable);
        return bookPage;
    }

    public Page<Book> jpaPageJPQLQuery(String bookName, Pageable pageable) {
        Page<Book> bookPage = bookRepository.jpaPageBookByNameJPQL(bookName, pageable);
        return bookPage;
    }

    public Page<Book> jpaPageJdbcTemplate(BookJpaPageParam param) {
        JdbcQuery jdbcQuery = bookRepository.getFindPageSql(param.getBookName());
        Page<Book> page = jdbcTemplateService.queryPage(jdbcQuery, param.getPageable(), Book.class);
        return page;
    }
}