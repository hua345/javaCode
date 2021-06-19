package com.github.chenjianhua.jpa.service;

import com.github.chenjianhua.common.jpa.support.JdbcQuery;
import com.github.chenjianhua.common.jpa.support.JdbcTemplateService;
import com.github.chenjianhua.jpa.model.Book;
import com.github.chenjianhua.jpa.param.BookJpaPageParam;
import com.github.chenjianhua.jpa.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
