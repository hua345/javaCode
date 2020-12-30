package com.github.chenjianhua.springbootexcel.excel.strategy;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.github.chenjianhua.springbootexcel.TestExportParam;
import com.github.chenjianhua.springbootexcel.bo.BeginAndEndTimeBo;
import com.github.chenjianhua.springbootexcel.enums.MyExcelTypeEnum;
import com.github.chenjianhua.springbootexcel.excel.ExcelExportTask;
import com.github.chenjianhua.springbootexcel.excel.ExcelStrategyHandler;
import com.github.chenjianhua.springbootexcel.excel.model.TestModel;
import com.github.chenjianhua.springbootexcel.model.ExcelExportHis;
import com.github.chenjianhua.springbootexcel.service.ExcelExportHisService;
import com.github.chenjianhua.springbootexcel.service.TestService;
import com.github.chenjianhua.springbootexcel.util.ExcelExportUtil;
import com.github.common.config.exception.MyRuntimeException;
import com.github.common.util.DateUtil;
import com.github.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author chenjianhua
 * @date 2020/12/23
 */
@Slf4j
@Component
public class TestAsyncStrategy extends AbstractExcelStrategy {
    private static final String EXCEL_FILE_NAME = "异步测试数据";

    private static final String SHEET_NAME = "sheet";

    @Resource
    private ExcelExportHisService excelExportHisService;

    @Resource
    private TestService testService;

    @Autowired
    public TestAsyncStrategy(ExcelStrategyHandler exportStrategyHandler) {
        super(null, MyExcelTypeEnum.TEST_ASYNC_EXPORT, exportStrategyHandler);
    }

    private static BigDecimal getDiffSecond(long startTimeMillis) {
        long nowTimeMillis = System.currentTimeMillis();
        BigDecimal bigDecimal = new BigDecimal(nowTimeMillis - startTimeMillis).setScale(2);
        BigDecimal diffSeconds = bigDecimal.divide(new BigDecimal(1000), RoundingMode.HALF_UP);
        return diffSeconds;
    }

    private Sheet createExcelSheet(Integer currentSheetNum) {
        Sheet currentSheet = new Sheet(currentSheetNum, 0, TestModel.class);
        currentSheet.setSheetName(SHEET_NAME + currentSheetNum);
        return currentSheet;
    }

    @Override
    public File export(ExcelExportTask task) {
        TestExportParam param = JsonUtil.toBean(JsonUtil.toJSONString(task.getExportArg()), TestExportParam.class);
        log.info("[{}]导出测试数据参数[{}]", task.getTaskNumber(), JsonUtil.toJSONString(param));
        long startTimeMillis = System.currentTimeMillis();
        Long exportCount = testService.countData(param);
        log.info("[{}]测试数据导出数据量：{},开始转换。", task.getTaskNumber(), exportCount);

        File tempFile = null;
        OutputStream outputStream = null;
        try {
            tempFile = Files.createTempFile(EXCEL_FILE_NAME, ".xlsx").toFile();
            outputStream = new FileOutputStream(tempFile, true);
        } catch (IOException e) {
            throw new MyRuntimeException("创建Temp文件失败," + e.getMessage());
        }
        ExcelWriter excelWriter = new ExcelWriter(outputStream, com.alibaba.excel.support.ExcelTypeEnum.XLSX, true);
        try {
            Long currentDataSize = 0L;
            Integer currentSheetNum = 1;
            Sheet currentSheet = createExcelSheet(currentSheetNum);
            List<BeginAndEndTimeBo> beginAndEndTimeBos = ExcelExportUtil.split(exportCount, param.getStartTradeTime(), param.getEndTradeTime());
            for (int index = 0; index < beginAndEndTimeBos.size(); index++) {
                BeginAndEndTimeBo beginAndEndTimeBo = beginAndEndTimeBos.get(index);
                param.setStartTradeTime(DateUtil.localDateTime2Date(beginAndEndTimeBo.getStartLocalDateTime()));
                param.setEndTradeTime(DateUtil.localDateTime2Date(beginAndEndTimeBo.getEndLocalDateTime()));
                List<TestModel> list = testService.findTestData(param);
                if (list == null) {
                    list = Collections.emptyList();
                }

                List<TestModel> rows = list.stream().map(item -> {
                    TestModel model = new TestModel();
                    BeanUtils.copyProperties(item, model);
                    return model;
                }).collect(Collectors.toList());

                ExcelExportHis excelExportHis = excelExportHisService.findByTaskNumber(task.getTaskNumber());
                if (excelExportHis != null) {
                    excelExportHis.setStatus(1);
                    int currentProgress = ((index + 1) * 100) / beginAndEndTimeBos.size();
                    excelExportHis.setProgress((index + 1) / beginAndEndTimeBos.size());
                    excelExportHis.setRemark("正在写入excel文件");
                    excelExportHisService.save(excelExportHis);
                    log.info("导出任务:[{}]当前进度:[{}]", excelExportHis.getTaskNumber(), currentProgress);
                }
                currentDataSize = currentDataSize + rows.size();
                Integer sheetNum = ExcelExportUtil.currentSheetNum(currentDataSize);
                if (!sheetNum.equals(currentSheetNum)) {
                    currentSheet = createExcelSheet(sheetNum);
                    currentSheetNum = sheetNum;
                    log.info("新增Excel Sheet页:{}", currentSheet.getSheetName());
                }
                excelWriter.write(rows, currentSheet);
            }
        } finally {
            //关闭writer的输出流
            excelWriter.finish();
        }

        log.info("[{}]测试数据导出完成耗时:{}秒", task.getTaskNumber(), getDiffSecond(startTimeMillis));
        return tempFile;
    }

}

