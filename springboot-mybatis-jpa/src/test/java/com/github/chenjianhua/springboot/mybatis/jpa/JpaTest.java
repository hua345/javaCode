package com.github.chenjianhua.springboot.mybatis.jpa;

import com.github.chenjianhua.common.json.util.JsonUtil;
import com.github.chenjianhua.springboot.mybatis.jpa.model.Book;
import com.github.chenjianhua.springboot.mybatis.jpa.param.BookJpaPageParam;
import com.github.chenjianhua.springboot.mybatis.jpa.service.BookJpaService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author chenjianhua
 * @date 2020/9/7
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
@ActiveProfiles("dev")
public class JpaTest {
    @Autowired
    private BookJpaService bookJpaService;

    @Test
    public void jpaTest() {
        Book book = new Book();
        book.setBookName("哈佛幸福课");
        book.setCreateTime(LocalDateTime.now());
        book.setUpdateTime(LocalDateTime.now());
        log.info("jpa保存book:{}", JsonUtil.toJsonString(book));
        bookJpaService.jpaSave(book);
        Optional<Book> result = bookJpaService.jpaFindById(book.getId());
        log.info("jpa查询结果book:{}", JsonUtil.toJsonString(result));
        result.get().setBookName(result.get().getBookName() + "version");
        bookJpaService.jpaSave(result.get());
        result = bookJpaService.jpaFindById(book.getId());
        log.info("jpa修改结果book:{}", JsonUtil.toJsonString(result));
    }

    @Test
    public void jpaPageJdbcTemplateTest() {
        BookJpaPageParam bookJpaPageParam = new BookJpaPageParam();
        bookJpaPageParam.setBookName("哈佛幸福课");
        bookJpaPageParam.setPage(2);
        bookJpaPageParam.setSize(2);
        List<String> orderList = new LinkedList<>();
        orderList.add("id:DESC");
        bookJpaPageParam.setOrders(orderList);
        Page<Book> page = bookJpaService.jpaPageJdbcTemplate(bookJpaPageParam);
        log.info("mybatis 分页信息:{}", JsonUtil.toJsonString(page));
    }

    @Test
    public void jpaPageNativeQueryTest() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        //分页
        Pageable pageable = PageRequest.of(2, 2, sort);
        Page<Book> page = bookJpaService.jpaPageNativeQuery("哈佛幸福课", pageable);
        log.info("mybatis 分页信息:{}", JsonUtil.toJsonString(page));
    }

    @Test
    public void jpaPageJPQLQueryTest() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        //分页
        Pageable pageable = PageRequest.of(2, 2, sort);
        Page<Book> page = bookJpaService.jpaPageJPQLQuery("哈佛幸福课", pageable);
        log.info("mybatis 分页信息:{}", JsonUtil.toJsonString(page));
    }
}
