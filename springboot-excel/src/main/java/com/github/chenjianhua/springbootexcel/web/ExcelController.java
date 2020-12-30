package com.github.chenjianhua.springbootexcel.web;

import com.github.chenjianhua.springbootexcel.enums.MyExcelTypeEnum;
import com.github.chenjianhua.springbootexcel.excel.ExcelExportTask;
import com.github.chenjianhua.springbootexcel.excel.ExcelServer;
import com.github.chenjianhua.springbootexcel.vo.request.ExportExcelParam;
import com.github.common.resp.ResponseVO;
import com.github.common.util.ResponseUtil;
import com.github.common.util.encrypt.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author chenjianhua
 * @date 2020/12/23
 */
@Slf4j
@RestController
public class ExcelController {

    @Resource
    private ExcelServer excelServer;

    /**
     * 异步导出
     */
    @PostMapping("/exportExcel/async")
    public ResponseVO exportExcelAsync(@Validated @RequestBody ExportExcelParam param) throws IOException {
        String taskNumber = UuidUtil.getUUID32();
        log.info("导出任务：{}", taskNumber);
        ExcelExportTask task = new ExcelExportTask();
        task.setExportArg(param.getExportArg());
        task.setExcelExportType(MyExcelTypeEnum.findByType(param.getExcelExportType()));
        task.setTaskNumber(taskNumber);
        task.setAsync(true);

        excelServer.export(task);
        return ResponseUtil.ok();
    }

    /**
     * 同步导出（同步下载）
     */
    @PostMapping("/exportExcel/sync")
    public ResponseVO exportExcelSync(@Validated @RequestBody ExportExcelParam param) throws IOException {
        String taskNumber = UuidUtil.getUUID32();
        log.info("同步导出任务：{}", taskNumber);
        ExcelExportTask task = new ExcelExportTask();
        task.setExportArg(param.getExportArg());
        task.setExcelExportType(MyExcelTypeEnum.findByType(param.getExcelExportType()));
        task.setTaskNumber(taskNumber);
        task.setAsync(false);
        return ResponseUtil.ok(excelServer.export(task));
    }
}

