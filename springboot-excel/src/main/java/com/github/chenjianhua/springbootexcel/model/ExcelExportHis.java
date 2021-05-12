package com.github.chenjianhua.springbootexcel.model;

import com.github.chenjianhua.springbootexcel.enums.ExcelExportStatusEnum;
import com.github.id.model.AbstractLongModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

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
    private String exportType;
    /**
     * 导出任务号
     */
    private String taskNumber;
    /**
     * 导出来源
     */
    private String taskOrigin;
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
    private String totalRecord;
    /**
     * 处理状态
     *
     * @see ExcelExportStatusEnum
     */
    private Integer exportStatus;
    /**
     * 进度
     */
    private Integer exportProgress;
    /**
     * 结果信息
     */
    private String resultMsg;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 导出参数
     */
    private String exportParam;
    /**
     * 是否为同步任务(0:异步任务,1:同步任务)
     */
    private boolean syncTask;
}
