package com.github.chenjianhua.common.jpa.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chenjianhua
 * @date 2020/9/7
 */
@Getter
@Setter
@ToString
public class PageVo<T> implements Serializable {
	/**
	 * 总记录数
	 */
	@JsonIgnore
	private int total;
	/**
	 * 当前页
	 */
	@JsonIgnore
	private int page = 1;
	/**
	 * 每页记录数，默认为10，最大每页500条
	 */
	@JsonIgnore
	private int size = 10;
	/**
	 * 排序
	 */
	@JsonIgnore
	private List<SortOrder> orders = new ArrayList<>();

	/**
	 * 总记录
	 */
	private List<T> rows;

	public void setTotal(int total) {
		this.total = total;
		int n = total % size;
		page = n > 0 ? total / size+1:total / size;
	}

	public void setPage(int page) {
		if(page <= 0) {
			page = 1;
		}
		this.page = page;
	}

	/**
	 * 组装分页返回
	 */
	@JsonView
	public Map<String, Object> getPage(){
		long n = total % size;

		Map<String, Object> page = new HashMap<>();
		page.put("total", this.total);
		page.put("page", this.page);
		page.put("size", this.size);
		page.put("totalPage", n > 0 ? total / size+1:total / size);
		page.put("sorts", orders);
		return page;
	}

	public PageVo(){}

	public static <T> PageVo<T> of(Page<T> queryPage){
		PageVo<T> vo = new PageVo<>();
		if(queryPage != null){
			vo.setRows(queryPage.getContent());
			vo.setTotal(Integer.parseInt(String.valueOf(queryPage.getTotalElements())));
			vo.setPage(queryPage.getNumber());
			vo.setSize(queryPage.getSize() > 0 ? queryPage.getSize() : 10);

			List<Sort.Order> orders = queryPage.getSort().toList();
			if(!CollectionUtils.isEmpty(orders)) {
				vo.setOrders(orders.stream().map(r -> SortOrder.of(r.getProperty(), r.getDirection())).collect(Collectors.toList()));
			}
		}
		return vo;
	}

	public static <T> PageVo<T> empty(){
		PageVo<T> vo = new PageVo<>();
		vo.setRows(Collections.emptyList());
		vo.setTotal(0);
		vo.setPage(0);
		vo.setSize(10);
		return vo;
	}


	public static <T> PageVo<T> of(T result){
		PageVo<T> vo = new PageVo<>();
		vo.setRows(Collections.singletonList(result));
		vo.setTotal(1);
		vo.setPage(1);
		vo.setSize(10);
		return vo;
	}

	public static <T> PageVo<T> of(T result, int pageSize){
		PageVo<T> vo = new PageVo<>();
		vo.setRows(Collections.singletonList(result));
		vo.setTotal(1);
		vo.setPage(1);
		vo.setSize(pageSize);
		return vo;
	}

	public static <T> PageVo<T> of(List<T> results, int pageSize){
		PageVo<T> vo = new PageVo<>();
		vo.setRows(results);
		vo.setTotal(1);
		vo.setPage(1);
		vo.setSize(pageSize);
		return vo;
	}
}
