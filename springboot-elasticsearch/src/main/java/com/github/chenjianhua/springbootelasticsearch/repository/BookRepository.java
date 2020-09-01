package com.github.chenjianhua.springbootelasticsearch.repository;

import com.github.chenjianhua.springbootelasticsearch.model.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author chenjianhua
 * @date 2020/9/1
 */
public interface BookRepository extends ElasticsearchRepository<Book,String> {
}
