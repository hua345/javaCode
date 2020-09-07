package com.github.spring.boot.idleaf.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author chenjianhua
 * @date 2020/9/7
 */
@Slf4j
@Data
public class Book {
    @JsonProperty("BookName")
    private String bookName;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date bookDate;
    @JsonIgnore
    private int num;

    public static void main(String[] args) {
        Book book = new Book();
        book.setBookName("刻意练习");
        book.setBookDate(new Date());
        book.setNum(2);
        String jsonString = JsonUtil.toPrettyJSONString(book);
        List<Book> books = new LinkedList<>();
        books.add(book);
        log.info("book:{}", jsonString);
        log.info("books:{}", JsonUtil.toPrettyJSONString(books));
        Book deserializeBook = JsonUtil.toBean(jsonString, Book.class);
        log.info(JsonUtil.toJSONString(deserializeBook));
    }
}