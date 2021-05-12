package com.github.chenjianhua.springbootexcel.model;

import com.github.id.model.AbstractLongModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author chenjianhua
 * @date 2020/12/23
 */
@Setter
@Getter
@Entity
@Table
public class ExcelImportHis extends AbstractLongModel {
    /**
     * 导出类型
     */
    private String importType;
    /**
     * 任务号
     */
    private String taskNumber;
    /**
     * 导入来源
     */
    private String taskOrigin;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 导出总记录数
     */
    private String totalRecord;
    /**
     * 状态;处理状态
     */
    private Integer importStatus;
    /**
     * 结果信息
     */
    private String resultMsg;
    /**
     * 导入文件地址
     */
    private String importFilePath;
    /**
     * 导入结果文件地址
     */
    private String resultFilePath;
    /**
     * 成功记录数
     */
    private int successRecord;
    /**
     * 失败记录数
     */
    private int failedRecord;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 导入参数
     */
    private String importParam;
    /**
     * 是否为同步任务(0:异步任务,1:同步任务)
     */
    private boolean syncTask;

}

