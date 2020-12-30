package com.github.chenjianhua.springbootexcel.excel.strategy;


import com.github.chenjianhua.springbootexcel.enums.MyExcelTypeEnum;
import com.github.chenjianhua.springbootexcel.excel.ExcelExportTask;
import com.github.chenjianhua.springbootexcel.excel.ExcelStrategyHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * @author haiping.huang
 * @time 2019/5/10
 */
@Slf4j
@Getter
public abstract class AbstractExcelStrategy implements ExportStrategy {

    protected MyExcelTypeEnum excelExportType;

    protected MyExcelTypeEnum excelImportType;

    AbstractExcelStrategy(MyExcelTypeEnum excelImportType, MyExcelTypeEnum excelExportType, ExcelStrategyHandler excelStrategyHandler) {
        this.excelImportType = excelImportType;
        this.excelExportType = excelExportType;
        excelStrategyHandler.registerStrategy(this);
    }

    @Override
    public File export(ExcelExportTask task) {
        return null;
    }
}

