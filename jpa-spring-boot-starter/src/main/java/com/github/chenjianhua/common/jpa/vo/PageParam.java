package com.github.chenjianhua.common.jpa.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author chenjianhua
 * @date 2020/9/7
 */
@Setter
@Getter
@ToString
public class PageParam<T> {
	/**
	 * 页码
	 */
	private Integer page = 1;
	/**
	 * 每页大小
	 */
	private Integer size = 10;
	/**
	 * 多个字段排序，排序规则 -> 字段名:排序方式,字段名:排序方式 如：createAt:DESC,vmTime:ASC
	 */
	private List<String> orders;

	@JsonIgnore
	public Pageable getPageable() {
		List<Sort.Order> sortOrders = new ArrayList<>();
		Optional.ofNullable(orders).orElse(new ArrayList<>()).forEach(s -> {
			if(s.contains(":")) {
				String[] arr = s.split(":");
				sortOrders.add(Direction.ASC.name().equals(arr[1].toUpperCase()) ? Sort.Order.asc(arr[0]) : Sort.Order.desc(arr[0]));
			} else {
				//如果不带排序方式默认升序
				sortOrders.add(Sort.Order.asc(s));
			}
		});

		if(page <= 0) {
			page = 1;
		}

		if(CollectionUtils.isEmpty(sortOrders)) {
			return PageRequest.of(page - 1, size);
		} else {
			return PageRequest.of(page - 1, size, Sort.by(sortOrders));
		}
	}
}
