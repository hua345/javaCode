package com.github.chenjianhua.springboot.mybatis.jpa.mapper;

import com.github.chenjianhua.springboot.mybatis.jpa.model.Book;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author chenjianhua
 * @date 2020-09-07 15:41:49
 */
@Mapper
public interface BookMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Book record);

    int insertSelective(Book record);

    Book selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Book record);

    int updateByPrimaryKey(Book record);
}