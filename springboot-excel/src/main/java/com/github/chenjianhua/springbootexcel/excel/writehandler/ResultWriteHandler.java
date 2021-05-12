package com.github.chenjianhua.springbootexcel.excel.writehandler;

import com.alibaba.excel.util.StyleUtil;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.github.chenjianhua.springbootexcel.enums.ExcelConstants;
import com.github.chenjianhua.springbootexcel.util.ExcelStyleUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.StringUtils;


/**
 * @author chenjianhua
 * @date 2021/3/28
 */
@Slf4j
public class ResultWriteHandler implements RowWriteHandler {
    @Override
    public void beforeRowCreate(WriteSheetHolder var1, WriteTableHolder var2, Integer var3, Integer var4, Boolean var5) {

    }

    @Override
    public void afterRowCreate(WriteSheetHolder var1, WriteTableHolder var2, Row var3, Integer var4, Boolean var5) {

    }

    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer relativeRowIndex, Boolean isHead) {
        if (isHead) {
            return;
        }
        Cell cell = row.getCell(row.getLastCellNum() - 1);
        if (null == cell) {
            return;
        }
        String cellValue = cell.getStringCellValue();
        if (!StringUtils.hasText(cellValue)) {
            return;
        }
        Workbook workbook = writeSheetHolder.getSheet().getWorkbook();
        WriteCellStyle contentWriteCellStyle = ExcelStyleUtil.buildDefaultContentCellStyle();
        if (ExcelConstants.SUCCESS_MSG.equals(cellValue)) {
            // 设置背景颜色
            contentWriteCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            CellStyle cellStyle = StyleUtil.buildContentCellStyle(workbook, contentWriteCellStyle);
            cell.setCellStyle(cellStyle);
        } else {
            // 设置背景颜色
            contentWriteCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
            CellStyle cellStyle = StyleUtil.buildContentCellStyle(workbook, contentWriteCellStyle);
            cell.setCellStyle(cellStyle);
        }
    }
}