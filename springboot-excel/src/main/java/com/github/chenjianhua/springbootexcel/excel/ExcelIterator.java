package com.github.chenjianhua.springbootexcel.excel;

import java.util.List;

/**
 * @author chenjianhua
 * @date 2021/1/4
 */
public interface ExcelIterator {
   public List<Object> next();
   public boolean hasNext();
}
