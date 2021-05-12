package com.github.chenjianhua.springbootexcel.excel.eventlistener;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.github.chenjianhua.springbootexcel.enums.ExcelConstants;
import com.github.chenjianhua.springbootexcel.excel.model.UploadDataModel;
import com.github.chenjianhua.springbootexcel.excel.writehandler.CellColorSheetWriteHandler;
import com.github.chenjianhua.springbootexcel.excel.writehandler.DefaultStylesUtil;
import com.github.chenjianhua.springbootexcel.util.ExcelSheetUtil;
import com.github.chenjianhua.springbootexcel.util.ReflectUtil;
import com.github.common.config.exception.MyRuntimeException;
import com.github.common.util.JsonUtil;
import com.github.common.util.encrypt.UuidUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chenjianhua
 * @date 2021/3/22
 */
@Slf4j
@Getter
public abstract class AbstractReadAnalysisEventListener<T> extends AnalysisEventListener<T> {
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
    /**
     * 导出结果文件
     */
    private File tempFile;
    /**
     * excel写入流
     */
    private ExcelWriter excelWriter;
    private WriteSheet writeSheet;

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
            this.tempFile = Files.createTempFile(ExcelConstants.ResultName + UuidUtil.getUUID32(), ExcelTypeEnum.XLSX.getValue()).toFile();
        } catch (IOException e) {
            log.error("创建temp文件失败:{}", e);
        }
        // 动态设置表头
        Field[] fields = context.readWorkbookHolder().getClazz().getDeclaredFields();
        List<List<String>> tableHead = new LinkedList<>();
        for (Field field : fields) {
            ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
            tableHead.add(Arrays.asList(excelProperty.value()));
        }
//        List<List<String>> tableHead = this.headMap.values().stream().map(item -> {
//            List<String> columnHead = new LinkedList<>();
//            columnHead.add(item);
//            return columnHead;
//        }).collect(Collectors.toList());
        tableHead.add(Collections.singletonList(ExcelConstants.ResultName));
        this.writeSheet = ExcelSheetUtil.initExcelSheet(tableHead);

        this.excelWriter = EasyExcel.write(this.tempFile)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .registerWriteHandler(DefaultStylesUtil.defaultStyles())
                .registerWriteHandler(new CellColorSheetWriteHandler(tableHead.size() - 1))
//                .registerWriteHandler(new ResultWriteHandler())
                .build();
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param rowData one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(T rowData, AnalysisContext context) {
        String resultMsg = "";
        try {
            processData(rowData, context);
            successRecords++;
            resultMsg = ExcelConstants.SUCCESS_MSG;
        } catch (MyRuntimeException e) {
            failedRecords++;
            resultMsg = e.getMessage();
            log.error(String.format("excel导入第%d行%s", context.readRowHolder().getRowIndex(), e.getMessage()));
        } catch (Exception e) {
            failedRecords++;
            resultMsg = ExcelConstants.FAIL_MSG;
            log.error(String.format("excel导入第%d行%s", context.readRowHolder().getRowIndex(), e.getMessage()));
            log.error(e.getMessage(), e);
        }
        // 动态写入结果文件
        List<Object> rowCellList = ReflectUtil.getClassFieldValue(rowData);
        rowCellList.add(resultMsg);
        excelWriter.write(Collections.singletonList(rowCellList), writeSheet);
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
        excelWriter.finish();
        log.info(tempFile.getAbsolutePath());
    }

    public static void main(String[] args) {
        String fileName = "/Users/chenjianhua/Desktop" + File.separator + "商品交易明细报表1275169920506234600.xlsx";

        AbstractReadAnalysisEventListener ktDefaultAnalysisEventListener = new AbstractReadAnalysisEventListener<UploadDataModel>() {
            @Override
            public void processData(UploadDataModel rowData, AnalysisContext context) {
                log.info(JsonUtil.toJSONString(rowData));
            }
        };
        EasyExcel.read(fileName, UploadDataModel.class, ktDefaultAnalysisEventListener).sheet().doRead();
    }
}
