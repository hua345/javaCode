package com.github.chenjianhua.springbootexcel.excel.strategy;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.github.chenjianhua.springbootexcel.TestExportParam;
import com.github.chenjianhua.springbootexcel.enums.MyExcelTypeEnum;
import com.github.chenjianhua.springbootexcel.excel.ExcelExportTask;
import com.github.chenjianhua.springbootexcel.excel.ExcelStrategyHandler;
import com.github.chenjianhua.springbootexcel.excel.model.TestCurrentModel;
import com.github.chenjianhua.springbootexcel.excel.model.TestModel;
import com.github.chenjianhua.springbootexcel.service.TestService;
import com.github.chenjianhua.springbootexcel.util.ExcelSheetUtil;
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
public class TestSimpleStrategy extends AbstractExcelStrategy {
    @Resource
    private TestService testService;

    @Autowired
    public TestSimpleStrategy(ExcelStrategyHandler exportStrategyHandler) {
        super(null, MyExcelTypeEnum.TEST_SIMPLE_EXPORT, exportStrategyHandler);
    }

    @Override
    public void export(ExcelExportTask task, ExcelWriter excelWriter) {
        TestExportParam param = JsonUtil.toBean(JsonUtil.toJSONString(task.getExportArg()), TestExportParam.class);
        WriteSheet currentSheet = ExcelSheetUtil.initExcelSheet(TestCurrentModel.class);
        List<TestModel> list = testService.findTestData(param);
        if (null == list) {
            list = Collections.emptyList();
        }
        List<TestCurrentModel> rows = list.stream().map(item -> {
            TestCurrentModel model = new TestCurrentModel();
            BeanUtils.copyProperties(item, model);
            return model;
        }).collect(Collectors.toList());
        excelWriter.write(rows, currentSheet);
    }
}

