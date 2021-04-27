package com.github.chenjianhua.common.jpa.vo;

import lombok.Data;
import org.springframework.data.domain.Sort;

/**
 * @author chenjianhua
 * @date 2020/9/7
 */
@Data
public class SortOrder {
	/**
	 * 字段名
	 */
	private String sortName;
	/**
	 * 排序方式
	 */
	private Sort.Direction sortOrder;

	public static SortOrder of(String sortName, Sort.Direction direction) {
		SortOrder jpaSortOrder = new SortOrder();
		jpaSortOrder.setSortName(sortName);
		jpaSortOrder.setSortOrder(direction);
		return jpaSortOrder;
	}
}
