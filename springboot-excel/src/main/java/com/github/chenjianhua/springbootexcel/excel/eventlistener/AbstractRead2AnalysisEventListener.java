package com.github.chenjianhua.springbootexcel.excel.eventlistener;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.github.chenjianhua.springbootexcel.enums.ExcelConstants;
import com.github.chenjianhua.springbootexcel.excel.model.UploadDataModel;
import com.github.common.config.exception.MyRuntimeException;
import com.github.common.util.JsonUtil;
import com.github.common.util.encrypt.UuidUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author chenjianhua
 * @date 2021/3/22
 */
@Slf4j
@Getter
public abstract class AbstractRead2AnalysisEventListener<T> extends AnalysisEventListener<T> {
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    /**
     * 导入成功条数
     */
    private Long successRecords = 0L;
    /**
     * 导入失败条数
     */
    private Long failedRecords = 0L;
    /**
     * 读取的数据列表
     */
    protected final List<T> rows = new LinkedList<>();
    /**
     * 表头
     */
    private Map<Integer, String> headMap;
    private Workbook workbook;
    private Sheet sheet;
    private short lastCellNum;

    private CellStyle successStyle;
    private CellStyle errorStyle;

    public AbstractRead2AnalysisEventListener() {

    }

    /**
     * 这里会一行行的返回头
     *
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        log.info("解析到一条头数据:{}", JsonUtil.toJSONString(headMap));
        this.headMap = headMap;
        try {
            if (null != context.readWorkbookHolder().getInputStream()) {
                this.workbook = WorkbookFactory.create(context.readWorkbookHolder().getInputStream());
            } else if (null != context.readWorkbookHolder().getFile()) {
                this.workbook = WorkbookFactory.create(context.readWorkbookHolder().getFile());
            }
        } catch (Exception e) {
            throw new MyRuntimeException("文件格式错误");
        }

        sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(0);
        lastCellNum = row.getLastCellNum();
        Cell cell1 = row.createCell(lastCellNum + 1);
        cell1.setCellValue("结果");

        this.successStyle = workbook.createCellStyle();
        successStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        successStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        this.errorStyle = workbook.createCellStyle();
        errorStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        errorStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param rowData one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(T rowData, AnalysisContext context) {
        Integer rowNum = context.readRowHolder().getRowIndex();
        try {
            processData(rowData, context);
            successRecords++;
            setResult(rowNum, ExcelConstants.SUCCESS_MSG, null, this.successStyle);
        } catch (MyRuntimeException e) {
            failedRecords++;
            log.error(String.format("excel导入第%d行%s", rowNum, e.getMessage()));
            setResult(rowNum, ExcelConstants.FAIL_MSG, e.getMessage(), this.successStyle);

        } catch (Exception e) {
            failedRecords++;
            setResult(rowNum, ExcelConstants.FAIL_MSG, null, this.successStyle);
            log.error(String.format("excel导入第%d行%s", rowNum, e.getMessage()));
            log.error(e.getMessage(), e);
        }

    }

    private void setResult(int rowNum, String result, String message, CellStyle cellStyle) {
        Row row = this.sheet.getRow(rowNum);
        Cell cell1 = row.createCell(this.lastCellNum + 1);
        cell1.setCellValue(result);
        cell1.setCellStyle(cellStyle);

        if (StringUtils.hasText(message)) {
            Cell cell2 = row.createCell(this.lastCellNum + 2);
            cell2.setCellValue(message);
            cell2.setCellStyle(cellStyle);
        }
    }

    /**
     * 需要实现的处理行数据方法
     */
    public abstract void processData(T t, AnalysisContext context);

    /**
     * 所有数据解析完成了 都会来调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("所有数据解析完成！");
        try {
            File resultFile = File.createTempFile(ExcelConstants.ResultName + UuidUtil.getUUID32(), ".xlsx");
            FileOutputStream fos = new FileOutputStream(resultFile);
            workbook.write(fos);
            fos.flush();
            fos.close();
            workbook.close();
            log.info(resultFile.getAbsolutePath());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new MyRuntimeException(e.getMessage());
        }
    }

    public static void main(String[] args) {
        AbstractRead2AnalysisEventListener ktDefaultAnalysisEventListener = new AbstractRead2AnalysisEventListener<UploadDataModel>() {
            @Override
            public void processData(UploadDataModel rowData, AnalysisContext context) {
                log.info(JsonUtil.toJSONString(rowData));
            }
        };
        String fileName = "/Users/chenjianhua/Desktop" + File.separator + "商品交易明细报表1275169920506234600.xlsx";

        EasyExcel.read(fileName, UploadDataModel.class, ktDefaultAnalysisEventListener).sheet().doRead();
    }
}
