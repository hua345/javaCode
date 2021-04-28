package com.github.chenjianhua.springboot.jdbc.mybatis.mapper;

import com.github.chenjianhua.springboot.jdbc.model.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author chenjianhua
 * @date 2020-09-07 15:41:49
 */
@Mapper
public interface BookMapper {
    /**
     * 分页查找所有图书
     */
    @Select("select * from book")
    List<Book> listBookPage(int pageSize, int pageNum);

    List<Book> selectAllBook(String bookName);

    int deleteByPrimaryKey(Long id);

    int insert(Book record);

    int insertSelective(Book record);

    Book selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Book record);

    int updateByPrimaryKey(Book record);
}