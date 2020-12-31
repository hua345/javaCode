package com.github.chenjianhua.springbootexcel.excel;

import com.github.chenjianhua.springbootexcel.enums.ExcelExportStatusEnum;
import com.github.chenjianhua.springbootexcel.enums.MyExcelTypeEnum;
import com.github.chenjianhua.springbootexcel.excel.strategy.AbstractExcelStrategy;
import com.github.chenjianhua.springbootexcel.excel.strategy.ExportStrategy;
import com.github.chenjianhua.springbootexcel.model.ExcelExportHis;
import com.github.chenjianhua.springbootexcel.service.ExcelExportHisService;
import com.github.common.config.exception.MyRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
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

    public ExportCallback export(ExcelExportTask task) {
        ExportCallback callback = new ExportCallback();
        callback.setTaskNumber(task.getTaskNumber());
        ExportStrategy executeStrategy = exportStrategyMap.get(task.getExcelExportType());
        if (executeStrategy == null) {
            log.error("任务:[{}] 类型:[{}]没有执行策略", task.getTaskNumber(), task.getExcelExportType().getDescription());
            callback.setCode(500);
            return callback;
        }
        ExcelExportHis excelExportHis = excelExportHisService.findByTaskNumber(task.getTaskNumber());
        excelExportHis.setExportStatus(ExcelExportStatusEnum.DOING.getType());
        excelExportHis.setRemark("正在获取导出数据");
        excelExportHisService.save(excelExportHis);
        File file = null;
        try {
            file = executeStrategy.export(task);
        } catch (MyRuntimeException e) {
            log.info("[{}]导出任务处理失败: {}", task.getTaskNumber(), e.getMessage());
            callback.setMsg(e.getMessage());
            callback.setCode(500);
            return callback;
        } catch (Exception e) {
            log.info("导出异常:{}", e);
            throw e;
        }
        if (Objects.isNull(file)) {
            log.error("导出任务{}文件为空", task.getTaskNumber());
            callback.setCode(500);
            return callback;
        }
        //上传文件
        excelExportHis.setExportStatus(ExcelExportStatusEnum.DOING.getType());
        excelExportHis.setProgress(80);
        excelExportHis.setRemark("正在获取导出数据");
        excelExportHisService.save(excelExportHis);

        log.info("上传文件:{}", file.getName());
        callback.setFileName(file.getName());
        callback.setFilePath(file.getName());
        return callback;
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

