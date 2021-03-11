package com.github.chenjianhua.springbootexcel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.chenjianhua.springbootexcel.bo.TableFieldInfoBo;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author chenjianhua
 * @date 2020/12/23
 */
@Data
public class TestExportParam {
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTradeTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTradeTime;

    /**
     * 动态字段列表
     */
    private List<TableFieldInfoBo> exportFields;
}
