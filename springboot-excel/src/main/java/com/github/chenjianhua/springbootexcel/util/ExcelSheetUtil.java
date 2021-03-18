package com.github.chenjianhua.springbootexcel.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.github.chenjianhua.springbootexcel.excel.model.TestCurrentModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author chenjianhua
 * @date 2021/1/4
 */
@Slf4j
public class ExcelSheetUtil {

    private static final String SHEET_NAME = "sheet";

    public static WriteSheet initExcelSheet(Class clazz) {
        return createExcelSheet(1,clazz);
    }

    public static WriteSheet initExcelSheet(List<List<String>> heads) {
        return createExcelSheet(1,heads);
    }

    public static WriteSheet createExcelSheet(Integer currentSheetNum, Class clazz) {
        WriteSheet currentSheet = EasyExcel.writerSheet(currentSheetNum, SHEET_NAME + currentSheetNum).head(clazz).build();
        return currentSheet;
    }

    public static WriteSheet createExcelSheet(Integer currentSheetNum, List<List<String>> tableHead) {
        WriteSheet currentSheet = EasyExcel.writerSheet(currentSheetNum, SHEET_NAME + currentSheetNum).head(tableHead).build();
        return currentSheet;
    }

    /**
     * 检查是否切换Sheet
     */
    public static WriteSheet checkSheet(Long currentDataSize, WriteSheet currentSheet, Class clazz) {
        return checkSheet(currentDataSize, currentSheet, clazz, null);
    }

    /**
     * 检查是否切换Sheet
     */
    public static WriteSheet checkSheet(Long currentDataSize, WriteSheet currentSheet, Class clazz, List<List<String>> tableHead) {
        Integer sheetNum = ExcelExportUtil.currentSheetNum(currentDataSize);
        if (!sheetNum.equals(currentSheet.getSheetNo())) {
            if (CollectionUtils.isEmpty(tableHead)) {
                currentSheet = ExcelSheetUtil.createExcelSheet(sheetNum, clazz);
            } else {
                currentSheet = ExcelSheetUtil.createExcelSheet(sheetNum, tableHead);
            }
            log.info("新增Excel Sheet页:{}", currentSheet.getSheetName());
        }
        return currentSheet;
    }
}
