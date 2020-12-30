package com.github.chenjianhua.springbootexcel.model;

import com.github.id.model.AbstractLongModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author chenjianhua
 * @date 2020/12/23
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class ExcelExportHis extends AbstractLongModel {

    /**
     * 导出类型
     */
    private String type;
    /**
     * 导出任务号
     */
    private String taskNumber;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 导出总记录数
     */
    private String total;
    /**
     * 状态;处理状态
     */
    private Integer status;
    /**
     * 进度
     */
    private Integer progress;
    /**
     * 备注
     */
    private String remark;
}
