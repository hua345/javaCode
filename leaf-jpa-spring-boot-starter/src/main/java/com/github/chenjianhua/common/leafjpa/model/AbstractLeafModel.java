package com.github.chenjianhua.common.leafjpa.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.chenjianhua.common.leafjpa.config.LeafJpaConstants;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chenjianhua
 * @date 2020/11/18
 * 标注为@MappedSuperclass的类将不是一个完整的实体类，他将不会映射到数据库表，但是他的属性都将映射到其子类的数据库字段中。
 * 标注为@MappedSuperclass的类不能再标注@Entity或@Table注解，也无需实现序列化接口。
 */
@Setter
@Getter
@MappedSuperclass
public class AbstractLeafModel implements Serializable {
    /**
     * 使用leaf叶子算法根据表名自动生成Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = LeafJpaConstants.LEAF_ID)
    @GenericGenerator(name = LeafJpaConstants.LEAF_ID, strategy = LeafJpaConstants.LEAF_CLASS_NAME)
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
    @Column(updatable = false, nullable = false)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime createTime;

    /**
     * 更新时间
     */
    @UpdateTimestamp
    @Column(nullable = false)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime updateTime;
}
