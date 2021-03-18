package com.github.chenjianhua.springbootexcel.excel.strategy;

import com.alibaba.excel.ExcelWriter;
import com.github.chenjianhua.springbootexcel.excel.ExcelExportTask;

import java.io.File;

/**
 * @author chenjianhua
 * @date 2020/12/23
 */
@FunctionalInterface
public interface ExportStrategy {
    void export(ExcelExportTask task, ExcelWriter excelWriter);
}