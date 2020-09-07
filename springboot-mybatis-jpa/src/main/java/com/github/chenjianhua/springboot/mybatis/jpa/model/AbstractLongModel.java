package com.github.chenjianhua.springboot.mybatis.jpa.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chenjianhua
 * @date 2020/9/7
 */
@Setter
@Getter
@MappedSuperclass
public class AbstractLongModel implements Serializable {

    @Id
    protected Long id;

    /**
     * 创建人
     */
    protected String creator;

    /**
     * 更新人
     */
    protected String updator;

    /**
     * 创建时间
     */
    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime createTime;

    /**
     * 更新时间
     */
    @UpdateTimestamp
    @Column(nullable = false)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime  updateTime;
}
