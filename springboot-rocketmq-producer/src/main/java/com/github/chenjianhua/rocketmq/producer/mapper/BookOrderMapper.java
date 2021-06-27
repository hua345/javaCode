package com.github.chenjianhua.rocketmq.producer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.chenjianhua.rocketmq.producer.model.BookOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author chenjianhua
 * @date 2021/6/26
 */
@Mapper
public interface BookOrderMapper extends BaseMapper<BookOrder> {

}
