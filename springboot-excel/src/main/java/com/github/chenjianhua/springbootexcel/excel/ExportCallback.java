package com.github.chenjianhua.springbootexcel.excel;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenjianhua
 * @date 2020/12/23
 */
@Getter
@Setter
public class ExportCallback {
    private Integer code = 200;
    private String msg;
    private String taskNumber;
    private String fileName;
    private Integer rowsSize;
    private String filePath;

    public ExportCallback() {
    }

    public ExportCallback(String fileName, Integer rowsSize) {
        this.fileName = fileName;
        this.rowsSize = rowsSize;
    }
}
