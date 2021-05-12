package com.github.chenjianhua.springbootexcel.excel.writehandler;

import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.github.chenjianhua.springbootexcel.util.ExcelStyleUtil;
import org.apache.poi.ss.usermodel.*;

/**
 * @author chenjianhua
 * @date 2021/3/29
 */
public class DefaultStylesUtil {
    public static HorizontalCellStyleStrategy defaultStyles() {
        WriteCellStyle headWriteCellStyle = ExcelStyleUtil.buildDefaultHeadCellStyle();

        WriteCellStyle contentWriteCellStyle = ExcelStyleUtil.buildDefaultContentCellStyle();
        // 设置背景颜色
        contentWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        // 初始化表格样式
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        return horizontalCellStyleStrategy;
    }
}
