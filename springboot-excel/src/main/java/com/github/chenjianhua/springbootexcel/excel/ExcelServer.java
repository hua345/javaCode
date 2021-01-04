package com.github.chenjianhua.springbootexcel.excel;

import com.github.chenjianhua.springbootexcel.enums.ExcelExportStatusEnum;
import com.github.chenjianhua.springbootexcel.model.ExcelExportHis;
import com.github.chenjianhua.springbootexcel.service.ExcelExportHisService;
import com.github.common.config.exception.MyRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.*;

/**
 * @author chenjianhua
 * @date 2020/12/23
 */
@Slf4j
@Component
public class ExcelServer implements ApplicationRunner {

    /**
     * todo - fix it
     */
    Executor executor = Executors.newFixedThreadPool(10);

    private final CompletionService<ExportCallback> exportExcelCompletionService = new ExecutorCompletionService<>(executor);


    @Resource
    private ExcelStrategyHandler exportStrategyHandler;

    @Resource
    private ExcelExportHisService excelExportHisService;
    /**
     * 同时支持15个导出
     */
    private Semaphore semaphore = new Semaphore(15);

    public String export(ExcelExportTask task) throws IOException {
        exportStrategyHandler.checkExportType(task);
        ExcelExportHis param = new ExcelExportHis();
        param.setTaskNumber(task.getTaskNumber());
        param.setExportType(task.getExcelExportType().getType());
        param.setProgress(0);
        param.setExportStatus(ExcelExportStatusEnum.DEFAULT.getType());
        excelExportHisService.save(param);

        try {
            semaphore.acquire();
            // 如果是异步导出任务或者当前正在执行的导出任务达到最大值
            if (task.isAsync()) {
                exportExcelCompletionService.submit(() -> exportStrategyHandler.export(task));
                return null;
            } else {
                ExportCallback callback = exportStrategyHandler.export(task);
                exportAfter(callback);

                return callback.getFilePath();
            }
        } catch (Exception e) {
            param.setExportStatus(ExcelExportStatusEnum.FAIL.getType());
            excelExportHisService.save(param);
            log.error("导出失败：{}", e);
            throw new MyRuntimeException("导出失败");
        } finally {
            if (!task.isAsync()) {
                semaphore.release();
            }
        }
    }

    private void doAfterExport() {
        while (true) {
            try {
                Future<ExportCallback> future = exportExcelCompletionService.take();
                try {
                    ExportCallback callback = future.get();
                    exportAfter(callback);
                } catch (ExecutionException e) {
                    log.error("获取导出结果异常", e);
                }
                Thread.sleep(500);
            } catch (InterruptedException e) {
                log.error("发生异常", e);
            } finally {
                semaphore.release();
            }
        }
    }

    /**
     * 更新导出任务
     */
    private void exportAfter(ExportCallback callback) {
        ExcelExportHis excelExportHis = excelExportHisService.findByTaskNumber(callback.getTaskNumber());
        if (StringUtils.hasText(callback.getFilePath())) {
            excelExportHis.setFilePath(callback.getFilePath());
            excelExportHis.setFileName(callback.getFileName());
            excelExportHis.setProgress(100);
            excelExportHis.setRemark(ExcelExportStatusEnum.SUCCESS.getDescription());
            excelExportHis.setExportStatus(ExcelExportStatusEnum.SUCCESS.getType());
        } else {
            excelExportHis.setProgress(100);
            excelExportHis.setRemark(callback.getMsg());
            excelExportHis.setExportStatus(ExcelExportStatusEnum.FAIL.getType());
        }
        excelExportHisService.save(excelExportHis);
        log.info("{}导出完成", callback.getTaskNumber());
    }

    @Async
    @Override
    public void run(ApplicationArguments args) {
        doAfterExport();
    }
}
