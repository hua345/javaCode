package com.github.chenjianhua.springbootexcel.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.github.chenjianhua.springbootexcel.excel.model.TestCurrentModel;

/**
 * @author chenjianhua
 * @date 2021/1/4
 */
public class ExcelSheetUtil {

    private static final String SHEET_NAME = "sheet";

    public static WriteSheet initExcelSheet() {
        WriteSheet currentSheet = EasyExcel.writerSheet(1, SHEET_NAME + 1).head(TestCurrentModel.class).build();
        return currentSheet;
    }

    public static WriteSheet createExcelSheet(Integer currentSheetNum) {
        WriteSheet currentSheet = EasyExcel.writerSheet(currentSheetNum, SHEET_NAME + currentSheetNum).head(TestCurrentModel.class).build();
        return currentSheet;
    }

    public static WriteSheet createExcelSheet(Integer currentSheetNum, String sheetName) {
        WriteSheet currentSheet = EasyExcel.writerSheet(currentSheetNum, sheetName).head(TestCurrentModel.class).build();
        return currentSheet;
    }
}
