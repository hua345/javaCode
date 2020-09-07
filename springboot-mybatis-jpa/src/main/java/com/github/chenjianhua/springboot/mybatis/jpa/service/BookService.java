package com.github.chenjianhua.springboot.mybatis.jpa.service;

import com.github.chenjianhua.springboot.mybatis.jpa.mapper.BookMapper;
import com.github.chenjianhua.springboot.mybatis.jpa.model.Book;
import com.github.chenjianhua.springboot.mybatis.jpa.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author chenjianhua
 * @date 2020/9/7
 */
@Slf4j
@Service
public class BookService {
    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookRepository bookRepository;

    public void mybatisSave(Book book) {
        bookMapper.insert(book);
    }

    public void jpaSave(Book book) {
        bookRepository.save(book);
    }

    public Book mybatisFindById(Long id) {
        return bookMapper.selectByPrimaryKey(id);
    }

    public Optional<Book> jpaFindById(Long id) {
        return bookRepository.findById(id);
    }
}
