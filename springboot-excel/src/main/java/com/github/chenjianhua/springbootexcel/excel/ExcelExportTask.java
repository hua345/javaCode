package com.github.chenjianhua.springbootexcel.excel;

import com.github.chenjianhua.springbootexcel.enums.MyExcelTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletResponse;

/**
 * @author chenjianhua
 * @date 2020/12/23
 */
@Getter
@Setter
public class ExcelExportTask {
    private String taskNumber;
    private MyExcelTypeEnum excelExportType;
    private Object exportArg;
    private boolean async;
    private HttpServletResponse response;

    public ExcelExportTask() {
    }
}