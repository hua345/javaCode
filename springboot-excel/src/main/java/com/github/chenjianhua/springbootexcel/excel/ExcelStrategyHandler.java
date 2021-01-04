package com.github.chenjianhua.springbootexcel.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.github.chenjianhua.springbootexcel.enums.ExcelExportStatusEnum;
import com.github.chenjianhua.springbootexcel.enums.MyExcelTypeEnum;
import com.github.chenjianhua.springbootexcel.excel.strategy.AbstractExcelStrategy;
import com.github.chenjianhua.springbootexcel.excel.strategy.ExportStrategy;
import com.github.chenjianhua.springbootexcel.model.ExcelExportHis;
import com.github.chenjianhua.springbootexcel.service.ExcelExportHisService;
import com.github.chenjianhua.springbootexcel.util.TestFileUtil;
import com.github.common.config.exception.MyRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenjianhua
 * @date 2020/12/23
 */
@Slf4j
@Component
public class ExcelStrategyHandler {

    private final Map<MyExcelTypeEnum, ExportStrategy> exportStrategyMap = new ConcurrentHashMap<>();

    @Resource
    private ExcelExportHisService excelExportHisService;

    private static BigDecimal getDiffSecond(long startTimeMillis) {
        long nowTimeMillis = System.currentTimeMillis();
        BigDecimal bigDecimal = new BigDecimal(nowTimeMillis - startTimeMillis).setScale(2);
        BigDecimal diffSeconds = bigDecimal.divide(new BigDecimal(1000), RoundingMode.HALF_UP);
        return diffSeconds;
    }

    public void checkExportType(ExcelExportTask task) {
        ExportStrategy executeStrategy = exportStrategyMap.get(task.getExcelExportType());
        if (executeStrategy == null) {
            log.error("任务:[{}] 类型:[{}]没有执行策略", task.getTaskNumber(), task.getExcelExportType().getDescription());
            throw new MyRuntimeException("没有查询到导出类型:" + task.getExcelExportType().getType());
        }
    }

    public static File createTempFile(ExcelExportTask task) {
        File tempFile = null;
        try {
            tempFile = Files.createTempFile(task.getExcelExportType().getDescription(), ".xlsx").toFile();
        } catch (IOException e) {
            log.error("创建Temp文件失败:{}", e);
            throw new MyRuntimeException("创建Temp文件失败");
        }
        return tempFile;
    }


    public ExcelWriter createExcelWriter(ExcelExportTask task, File tempFile) {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(tempFile, true);
        } catch (FileNotFoundException e) {
            log.error("创建文件文件流失败:{}", e);
            throw new MyRuntimeException("创建文件文件流失败");
        }
        ExcelWriter excelWriter = EasyExcel.write(outputStream).build();
        return excelWriter;
    }

    public ExportCallback export(ExcelExportTask task) {
        log.info("[{}]导出[{}]参数[{}]", task.getTaskNumber(), task.getExcelExportType().getDescription(), task.getExportArg());
        long startTimeMillis = System.currentTimeMillis();

        ExportCallback callback = new ExportCallback();
        callback.setTaskNumber(task.getTaskNumber());
        ExportStrategy executeStrategy = exportStrategyMap.get(task.getExcelExportType());
        // 查询导出任务
        ExcelExportHis excelExportHis = excelExportHisService.findByTaskNumber(task.getTaskNumber());
        excelExportHis.setExportStatus(ExcelExportStatusEnum.DOING.getType());
        excelExportHis.setRemark("正在获取导出数据");
        excelExportHisService.save(excelExportHis);
        File tempFile = createTempFile(task);
        ExcelWriter excelWriter = createExcelWriter(task, tempFile);
        try {
            executeStrategy.export(task, excelWriter);
        } catch (MyRuntimeException e) {
            log.info("[{}]导出任务处理失败: {}", task.getTaskNumber(), e.getMessage());
            callback.setMsg(e.getMessage());
            callback.setCode(500);
            return callback;
        } catch (Exception e) {
            log.info("导出异常:{}", e);
            throw e;
        } finally {
            if (Objects.nonNull(excelWriter)) {
                excelWriter.finish();
            }
        }
        if (Objects.isNull(tempFile)) {
            log.error("导出任务[{}]文件为空", task.getTaskNumber());
            callback.setCode(500);
            return callback;
        }
        //上传文件
        uploadFile(excelExportHis, tempFile, callback);
        // 删除文件
        tempFile.delete();
        log.info("[{}]导出[{}]完成耗时:{}秒", task.getTaskNumber(), task.getExcelExportType().getDescription(), getDiffSecond(startTimeMillis));
        return callback;
    }

    private void uploadFile(ExcelExportHis excelExportHis, File file, ExportCallback callback) {
        excelExportHis.setExportStatus(ExcelExportStatusEnum.DOING.getType());
        excelExportHis.setProgress(90);
        excelExportHis.setRemark("正在获取导出数据");
        excelExportHisService.save(excelExportHis);
        TestFileUtil.testUploadFile(file);
        log.info("上传文件:{}", file.getName());
        callback.setFileName(file.getName());
        callback.setFilePath(file.getName());
    }

    /**
     * 注册导出策略
     *
     * @param executeStrategy 策略实体
     */
    public void registerStrategy(AbstractExcelStrategy executeStrategy) {
        MyExcelTypeEnum excelExportType = executeStrategy.getExcelExportType();
        MyExcelTypeEnum excelImportType = executeStrategy.getExcelImportType();
        log.info("注册策略export:{}, import:{}", excelExportType, excelImportType);
        if (excelExportType != null) {
            synchronized (exportStrategyMap) {
                exportStrategyMap.put(excelExportType, executeStrategy);
            }
        }
    }

    /**
     * 删除策略
     */
    public void removeExportStrategy(MyExcelTypeEnum type) {
        if (type.getExcelType() == 0) {
            synchronized (exportStrategyMap) {
                exportStrategyMap.remove(type);
            }
        }
    }
}

