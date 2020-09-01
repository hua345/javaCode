package com.github.chenjianhua.springbootelasticsearch.model;

/**
 * @author chenjianhua
 * @date 2020/9/1
 */

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.Date;

@Data
@Document(indexName = "book_index3")
public class Book {
    @Id
    private String id;
    @Field
    private Date bookDate;
    @Field
    private String bookName;
}
