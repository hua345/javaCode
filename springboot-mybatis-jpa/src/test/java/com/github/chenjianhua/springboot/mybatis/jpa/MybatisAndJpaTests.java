package com.github.chenjianhua.springboot.mybatis.jpa;

import com.github.chenjianhua.springboot.mybatis.jpa.model.Book;
import com.github.chenjianhua.springboot.mybatis.jpa.service.BookService;
import com.github.common.util.JsonUtil;
import com.github.id.leaf.IdLeafRedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

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
    private IdLeafRedisService idLeafRedisService;

    @Autowired
    private BookService bookService;

    @Test
    public void MybatisTest() {
        Book book = new Book();
        book.setId(idLeafRedisService.getIdByBizTag("Book"));
        book.setBookName("刻意练习");
        book.setCreateTime(new Date());
        book.setUpdateTime(new Date());
        log.info("mybatis保存book:{}", JsonUtil.toJSONString(book));
        bookService.mybatisSave(book);
        Book result = bookService.mybatisFindById(book.getId());
        log.info("mybatis查询结果book:{}", JsonUtil.toJSONString(result));
    }

    @Test
    public void jpaTest() {
        Book book = new Book();
        book.setBookName("哈佛幸福课");
        book.setCreateTime(new Date());
        book.setUpdateTime(new Date());
        log.info("jpa保存book:{}", JsonUtil.toJSONString(book));
        bookService.jpaSave(book);
        Optional<Book> result = bookService.jpaFindById(book.getId());
        log.info("jpa查询结果book:{}", JsonUtil.toJSONString(result));
        result.get().setBookName(result.get().getBookName() + "version");
        bookService.jpaSave(result.get());
        result = bookService.jpaFindById(book.getId());
        log.info("jpa修改结果book:{}", JsonUtil.toJSONString(result));
    }
}
