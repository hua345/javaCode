package com.github.chenjianhua.springbootexcel.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.github.chenjianhua.springbootexcel.excel.model.TestCurrentModel;

import java.util.List;

/**
 * @author chenjianhua
 * @date 2021/1/4
 */
public class ExcelSheetUtil {

    private static final String SHEET_NAME = "sheet";

    public static WriteSheet initExcelSheet(Class clazz) {
        WriteSheet currentSheet = EasyExcel.writerSheet(1, SHEET_NAME + 1).head(clazz).build();
        return currentSheet;
    }

    public static WriteSheet initExcelSheet(List<List<String>> heads) {
        WriteSheet currentSheet = EasyExcel.writerSheet(1, SHEET_NAME + 1).head(heads).build();
        return currentSheet;
    }

    public static WriteSheet createExcelSheet(Integer currentSheetNum, Class clazz) {
        WriteSheet currentSheet = EasyExcel.writerSheet(currentSheetNum, SHEET_NAME + currentSheetNum).head(clazz).build();
        return currentSheet;
    }

    public static WriteSheet createExcelSheet(Integer currentSheetNum, String sheetName, Class clazz) {
        WriteSheet currentSheet = EasyExcel.writerSheet(currentSheetNum, sheetName).head(clazz).build();
        return currentSheet;
    }
}
