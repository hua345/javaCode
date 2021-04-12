package com.github.chenjianhua.springboot.mybatis.jpa.repository;

import com.github.chenjianhua.common.id.repository.BaseLongRepository;
import com.github.chenjianhua.springboot.mybatis.jpa.model.Book;
import org.springframework.stereotype.Repository;

/**
 * @author chenjianhua
 * @date 2020/9/7
 */
@Repository
public interface BookRepository extends BaseLongRepository<Book> {

}

