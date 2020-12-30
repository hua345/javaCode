package com.github.chenjianhua.springbootexcel.excel.strategy;

import com.github.chenjianhua.springbootexcel.excel.ExcelExportTask;

import java.io.File;

/**
 * @author chenjianhua
 * @date 2020/12/23
 */
@FunctionalInterface
public interface ExportStrategy {
    File export(ExcelExportTask task);
}

