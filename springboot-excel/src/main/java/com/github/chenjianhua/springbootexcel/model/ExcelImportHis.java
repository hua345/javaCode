package com.github.chenjianhua.springbootexcel.model;

import com.github.chenjianhua.springbootexcel.enums.ExcelExportStatusEnum;
import com.github.id.model.AbstractLongModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

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
     * 文件名
     */
    private String fileName;
    /**
     * 导入总记录数
     */
    private Integer total;

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
    private int successRecords;
    /**
     * 失败记录数
     */
    private int failedRecords;
    /**
     * 导入参数
     */
    private String importParam;
    /**
     * 错误信息
     */
    private String failedMsg;
    /**
     * 状态;处理状态
     */
    private Integer importStatus;
}

