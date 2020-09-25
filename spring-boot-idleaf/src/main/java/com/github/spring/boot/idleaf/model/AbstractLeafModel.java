package com.github.spring.boot.idleaf.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@MappedSuperclass
public class AbstractLeafModel implements Serializable {
    /**
     * 保存前设置一下Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "leafId")
    @GenericGenerator(name = "leafId", strategy = "com.github.spring.boot.idleaf.model.LeafId",parameters = {
            @Parameter(name = "bizTag", value = "testId")})
    private Long id;
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
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, nullable = false)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date createAt;

    /**
     * 更新时间
     */
    @UpdateTimestamp
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date updateAt;
    /**
     * 是否删除
     */
    @JsonIgnore
    protected Boolean deleted = false;
}
