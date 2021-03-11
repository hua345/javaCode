package com.github.chenjianhua.springbootexcel.excel.strategy;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.github.chenjianhua.springbootexcel.TestExportParam;
import com.github.chenjianhua.springbootexcel.bo.BeginAndEndTimeBo;
import com.github.chenjianhua.springbootexcel.bo.TableFieldInfoBo;
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
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chenjianhua
 * @date 2020/12/23
 */
@Slf4j
@Component
public class TestDynamicStrategy extends AbstractExcelStrategy {

    @Resource
    private TestService testService;

    @Autowired
    public TestDynamicStrategy(ExcelStrategyHandler exportStrategyHandler) {
        super(null, MyExcelTypeEnum.TEST_DYNAMIC_EXPORT, exportStrategyHandler);
    }

    @Override
    public void export(ExcelExportTask task, ExcelWriter excelWriter) {
        TestExportParam param = JsonUtil.toBean(JsonUtil.toJSONString(task.getExportArg()), TestExportParam.class);
//        List<TableFieldInfoBo> tableFieldInfoBos = testService.findTableFieldInfoBo();
        List<TableFieldInfoBo> tableFieldInfoBos = param.getExportFields();
        if(CollectionUtils.isEmpty(tableFieldInfoBos)){
            return;
        }
        List<TableFieldInfoBo> sortedTableFieldInfoBos = tableFieldInfoBos.stream().filter(item -> Objects.nonNull(item.getDisplayStatus()) && item.getDisplayStatus()).sorted(Comparator.comparing(TableFieldInfoBo::getIndex)).collect(Collectors.toList());
        // 动态设置表头
        List<List<String>> tableHead = sortedTableFieldInfoBos.stream().map(item -> {
            List<String> columnHead = new ArrayList<>(4);
            columnHead.add(item.getFieldName());
            return columnHead;
        }).collect(Collectors.toList());
        WriteSheet currentSheet = ExcelSheetUtil.initExcelSheet(tableHead);

        List<TestModel> list = testService.findSimpleTestData(param);
        if (null == list) {
            list = Collections.emptyList();
        }
        // 动态设置行数据
        List<List<Object>> rows = list.stream().map(item -> {
            Map dataMap = JsonUtil.toBean(JsonUtil.toJSONString(item), HashMap.class);
            List<Object> valueData = new LinkedList<>();
            sortedTableFieldInfoBos.stream().forEach(fieldItem -> {
                valueData.add(dataMap.getOrDefault(fieldItem.getFieldCode(), null));
            });
            return valueData;
        }).collect(Collectors.toList());
        excelWriter.write(rows, currentSheet);
    }
}

