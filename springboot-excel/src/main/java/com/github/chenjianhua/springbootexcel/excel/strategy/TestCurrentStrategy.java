package com.github.chenjianhua.springbootexcel.excel.strategy;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.github.chenjianhua.springbootexcel.TestExportParam;
import com.github.chenjianhua.springbootexcel.bo.BeginAndEndTimeBo;
import com.github.chenjianhua.springbootexcel.enums.ExcelExportStatusEnum;
import com.github.chenjianhua.springbootexcel.enums.MyExcelTypeEnum;
import com.github.chenjianhua.springbootexcel.excel.ExcelExportTask;
import com.github.chenjianhua.springbootexcel.excel.ExcelStrategyHandler;
import com.github.chenjianhua.springbootexcel.excel.model.TestCurrentModel;
import com.github.chenjianhua.springbootexcel.excel.model.TestModel;
import com.github.chenjianhua.springbootexcel.model.ExcelExportHis;
import com.github.chenjianhua.springbootexcel.service.ExcelExportHisService;
import com.github.chenjianhua.springbootexcel.service.TestService;
import com.github.chenjianhua.springbootexcel.util.ExcelExportUtil;
import com.github.chenjianhua.springbootexcel.util.ExcelSheetUtil;
import com.github.common.util.DateUtil;
import com.github.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenjianhua
 * @date 2020/12/23
 */
@Slf4j
@Component
public class TestCurrentStrategy extends AbstractExcelStrategy {

    @Resource
    private ExcelExportHisService excelExportHisService;

    @Resource
    private TestService testService;

    @Autowired
    public TestCurrentStrategy(ExcelStrategyHandler exportStrategyHandler) {
        super(null, MyExcelTypeEnum.TEST_CURRENT_EXPORT, exportStrategyHandler);
    }

    @Override
    public void export(ExcelExportTask task, ExcelWriter excelWriter) {
        TestExportParam param = JsonUtil.toBean(JsonUtil.toJSONString(task.getExportArg()), TestExportParam.class);
        ExcelExportHis excelExportHis = excelExportHisService.findByTaskNumber(task.getTaskNumber());

        Long exportCount = testService.countData(param);
        log.info("[{}]导出{}数据量：{},开始转换。", task.getTaskNumber(), this.excelExportType.getDescription(), exportCount);
        Long currentDataSize = 0L;
        WriteSheet currentSheet = ExcelSheetUtil.initExcelSheet(TestCurrentModel.class);
        List<BeginAndEndTimeBo> beginAndEndTimeBos = ExcelExportUtil.split(task.getTaskNumber(), exportCount, param.getStartTradeTime(), param.getEndTradeTime());
        for (int index = 0; index < beginAndEndTimeBos.size(); index++) {
            BeginAndEndTimeBo beginAndEndTimeBo = beginAndEndTimeBos.get(index);
            param.setStartTradeTime(DateUtil.localDateTime2Date(beginAndEndTimeBo.getStartLocalDateTime()));
            param.setEndTradeTime(DateUtil.localDateTime2Date(beginAndEndTimeBo.getEndLocalDateTime()));
            List<TestModel> list = testService.findTestData(param);
            if (list == null) {
                list = Collections.emptyList();
            }
            List<TestCurrentModel> rows = list.stream().map(item -> {
                TestCurrentModel model = new TestCurrentModel();
                BeanUtils.copyProperties(item, model);
                return model;
            }).collect(Collectors.toList());
            // 检查是否切换Sheet
            currentDataSize = currentDataSize + rows.size();
            currentSheet = ExcelSheetUtil.checkSheet(currentDataSize, currentSheet,TestCurrentModel.class);
            // 处理导出进度
            processHandle(excelExportHis, beginAndEndTimeBos, index);
            excelWriter.write(rows, currentSheet);
        }
    }

    /**
     * 处理导出进度
     */
    private void processHandle(ExcelExportHis excelExportHis, List<BeginAndEndTimeBo> beginAndEndTimeBos, Integer index) {
        if (excelExportHis != null) {
            excelExportHis.setExportStatus(ExcelExportStatusEnum.DOING.getType());
            int currentProgress = ((index + 1) * 100) / beginAndEndTimeBos.size();
            excelExportHis.setExportProgress((index + 1) / beginAndEndTimeBos.size());
            excelExportHis.setResultMsg("正在写入excel文件");
            excelExportHisService.save(excelExportHis);
            log.info("导出任务:[{}]当前进度:[{}]", excelExportHis.getTaskNumber(), currentProgress);
        }
    }

}

