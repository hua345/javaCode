package com.github.chenjianhua.common.mybatisplus.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author chenjianhua
 * @date 2021/4/23
 */
@Setter
@Getter
@ToString
public class PageParam<T> {
    /**
     * 页码
     */
    private int page = 1;
    /**
     * 每页大小
     */
    private int size = 10;
    /**
     * 多个字段排序 排序规则 -> 字段名:排序方式,字段名:排序方式 如：createAt:DESC,vmTime:ASC
     */
    private List<SortOrder> orders;

    /**
     * 分页对象
     */
    public IPage<T> getPageable() {
        Page<T> p = new Page<>(page, size);
        if (CollectionUtils.isNotEmpty(orders)) {
            orders.forEach(s ->
                    p.addOrder(new OrderItem(StringUtils.camelToUnderline(s.getSortName()),
                            s.getSortOrder() == Direction.ASC)));
        }
        return p;
    }
}
