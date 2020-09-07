package com.github.chenjianhua.springboot.mybatis.jpa;

import com.github.chenjianhua.springboot.mybatis.jpa.model.Book;
import com.github.chenjianhua.springboot.mybatis.jpa.service.BookService;
import com.github.chenjianhua.springboot.mybatis.jpa.service.idleaf.IdLeafRedisServiceImpl;
import com.github.chenjianhua.springboot.mybatis.jpa.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

/**
 * @author chenjianhua
 * @date 2020/9/7
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@ActiveProfiles("dev")
public class MybatisAndJpaTests {
    @Autowired
    private IdLeafRedisServiceImpl idLeafRedisService;

    @Autowired
    private BookService bookService;

    @Test
    public void MybatisTest() {
        Book book = new Book();
        book.setId(idLeafRedisService.getIdByBizTag("book"));
        book.setBookName("刻意练习");
        book.setCreateTime(LocalDateTime.now());
        book.setUpdateTime(LocalDateTime.now());
        log.info("mybatis保存book:{}", JsonUtil.toJSONString(book));
        bookService.mybatisSave(book);
        Book result = bookService.mybatisFindById(book.getId());
        log.info("mybatis查询结果book:{}", JsonUtil.toJSONString(result));
    }
    @Test
    public void jpaTest() {
        Book book = new Book();
        book.setId(idLeafRedisService.getIdByBizTag("book"));
        book.setBookName("哈佛幸福课");
        book.setCreateTime(LocalDateTime.now());
        book.setUpdateTime(LocalDateTime.now());
        log.info("jpa保存book:{}", JsonUtil.toJSONString(book));
        bookService.mybatisSave(book);
        Book result = bookService.mybatisFindById(book.getId());
        log.info("jpa查询结果book:{}", JsonUtil.toJSONString(result));
    }
}
