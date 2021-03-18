package com.github.chenjianhua.springbootexcel.excel;


import com.github.chenjianhua.springbootexcel.enums.MyExcelTypeEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author chenjianhua
 * @date 2020/12/23
 */
@Setter
@Getter
public class ExcelImportTask {
    /**
     * 任务号
     */
    private String taskNumber;
    /**
     * 任务类型
     */
    private MyExcelTypeEnum excelImportType;
    /**
     * 导入参数
     */
    private Map<String, String[]> importArg;
    /**
     * 上传文件
     */
    private MultipartFile file;
}

