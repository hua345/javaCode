package com.github.chenjianhua.common.mybatis.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chenjianhua
 * @date 2021/4/26
 */
@Setter
@Getter
@MappedSuperclass
public abstract class AbstractLongModel implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Long id;

    protected String creator;

    protected String updator;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected LocalDateTime updateTime;
}
