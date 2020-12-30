package com.github.chenjianhua.springbootexcel.bo;

import com.fasterxml.jackson.annotation.JsonValue;
import com.github.common.util.DateFormatEnum;
import com.github.common.util.DateUtil;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author chenjianhua
 * @date 2020/12/22
 */
@Data
public class BeginAndEndTimeBo {

    private LocalDateTime startLocalDateTime;

    private LocalDateTime endLocalDateTime;

    @JsonValue
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{'startLocalDateTime':'").append(DateUtil.formatDateTime(startLocalDateTime, DateFormatEnum.DATE_YYYY_MM_DD_HH_MM_SS));
        sb.append("','endLocalDateTime':'").append(DateUtil.formatDateTime(endLocalDateTime, DateFormatEnum.DATE_YYYY_MM_DD_HH_MM_SS));
        sb.append("'}");
        return sb.toString();
    }
}
