package com.github.chenjianhua.springbootexcel.repository;

import com.github.chenjianhua.springbootexcel.model.ExcelExportHis;
import com.github.id.repository.BaseLongRepository;
import org.springframework.stereotype.Repository;

/**
 * @author chenjianhua
 * @date 2020/12/23
 */
@Repository
public interface ExcelExportHisRepository extends BaseLongRepository<ExcelExportHis> {
    ExcelExportHis findByTaskNumber(String taskNumber);

}
