package com.github.chenjianhua.springbootexcel.vo.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author chenjianhua
 * @date 2020/12/30
 */
@Data
public class ExportExcelParam {
    @NotBlank(message = "任务类型不能为空")
    private String excelExportType;
    private Object exportArg;
}
