package com.github.chenjianhua.springboot.mybatis.jpa.mapper;

import com.github.chenjianhua.springboot.mybatis.jpa.model.LeafAlloc;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author chenjianhua
 * @date 2020-09-07 15:41:49
 */
@Mapper
public interface LeafAllocMapper {
    /**
     * 查询所有数据
     */
    List<LeafAlloc> selectAllLeafAlloc();

    @Update("UPDATE leaf_alloc SET max_id = max_id + step WHERE biz_tag = #{tag}")
    void updateMaxId(@Param("tag") String tag);

    int deleteByPrimaryKey(String bizTag);

    int insert(LeafAlloc record);

    LeafAlloc selectByPrimaryKey(String bizTag);

    int updateByPrimaryKey(LeafAlloc record);
}