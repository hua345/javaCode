package com.github.chenjianhua.springbootexcel.service;

import com.github.chenjianhua.springbootexcel.model.ExcelExportHis;
import com.github.chenjianhua.springbootexcel.repository.ExcelExportHisRepository;
import com.github.id.repository.BaseLongRepository;
import com.github.id.service.AbstractLongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author chenjianhua
 * @date 2020/12/23
 */
@Service
public class ExcelExportHisService extends AbstractLongService<ExcelExportHis> {
    private ExcelExportHisRepository excelExportHisRepository;

    @Autowired
    public ExcelExportHisService(ExcelExportHisRepository excelExportHisRepository) {
        super(excelExportHisRepository);
        this.excelExportHisRepository = excelExportHisRepository;
    }

    public ExcelExportHis findByTaskNumber(String taskNumber) {
        return excelExportHisRepository.findByTaskNumber(taskNumber);
    }

}
