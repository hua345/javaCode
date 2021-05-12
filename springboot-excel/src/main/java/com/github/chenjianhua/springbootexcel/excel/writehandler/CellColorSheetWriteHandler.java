package com.github.chenjianhua.springbootexcel.excel.writehandler;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.util.StyleUtil;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.github.chenjianhua.springbootexcel.enums.ExcelConstants;
import com.github.chenjianhua.springbootexcel.util.ExcelStyleUtil;
import org.apache.poi.ss.usermodel.*;

import java.util.List;

/**
 * @author chenjianhua
 * @date 2021/3/29
 */
public class CellColorSheetWriteHandler implements CellWriteHandler {

    private int cellIndex;

    /**
     * 无参构造
     */
    public CellColorSheetWriteHandler(int handleCellIndex) {
        this.cellIndex = handleCellIndex;
    }

    /**
     * 在创建单元格之前调用
     */
    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {
    }

    /**
     * 在单元格创建后调用
     */
    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
    }

    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, CellData cellData, Cell cell, Head head, Integer integer, Boolean aBoolean) {

    }

    /**
     * 在单元上的所有操作完成后调用
     */
    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        String cellValue = cell.getStringCellValue();
        // 不处理第一行
        if (!isHead && cellIndex == cell.getColumnIndex()) {
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
}
