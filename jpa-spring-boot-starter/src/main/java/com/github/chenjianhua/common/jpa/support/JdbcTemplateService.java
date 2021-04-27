package com.github.chenjianhua.common.jpa.support;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.PagerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chenjianhua
 * @date 2020/9/7
 */
@Slf4j
@Component
public class JdbcTemplateService {

    private final DbType dbType = DbType.mysql;

    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcTemplateService(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     * 分页查询
     *
     * @param jdbcQuery 查询对象
     * @param pageable  分页参数
     * @param clazz     查询结果模型class对象
     */
    public <T> Page<T> queryPage(JdbcQuery jdbcQuery, Pageable pageable, Class<T> clazz) {
        StringBuilder sql = new StringBuilder(jdbcQuery.getQuerySql());
        Map<String, Object> paramMap = jdbcQuery.getArgsMap();
        //参数不能使用?，查询总数SQL优化
        String countSql = PagerUtils.count(sql.toString(), dbType).replaceAll("\n", " ");
        log.info("查询总数SQL：{}", countSql);
        long total = namedParameterJdbcTemplate.queryForObject(countSql, paramMap, Long.class);
        List<T> result;
        if (total > 0) {
            Sort sort = pageable.getSort();
            if (sort.isSorted()) {
                String order = pageable.getSort().stream().map(p -> p.getProperty().concat(" ").concat(p.getDirection().name())).collect(Collectors.joining(","));
                sql.append(" order by ").append(order);
            }
            sql.append(" limit :_offset, :_length");
            paramMap.put("_offset", pageable.getOffset());
            paramMap.put("_length", pageable.getPageSize());
            result = namedParameterJdbcTemplate.query(sql.toString(), paramMap, BeanPropertyRowMapper.newInstance(clazz));
        } else {
            result = Collections.emptyList();
        }
        return new PageImpl<>(result, pageable, total);
    }

    /**
     * 列表查询
     *
     * @param jdbcQuery 查询对象
     * @param clazz     查询结果模型class对象
     */
    public <T> List<T> queryList(JdbcQuery jdbcQuery, Class<T> clazz) {
        List<T> list = namedParameterJdbcTemplate.query(jdbcQuery.getQuerySql(), jdbcQuery.getArgsMap(), BeanPropertyRowMapper.newInstance(clazz));
        return CollectionUtils.isEmpty(list) ? Collections.emptyList() : list;
    }

    /**
     * 列表查询
     *
     * @param sql   查询SQL
     * @param clazz 查询结果模型class对象
     */
    public <T> List<T> queryList(String sql, Class<T> clazz) {
        List<T> list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(clazz));
        return CollectionUtils.isEmpty(list) ? Collections.emptyList() : list;
    }

    /**
     * 单个值
     *
     * @param jdbcQuery 查询对象
     * @param clazz     查询结果模型class对象
     */
    public <T> T queryOne(JdbcQuery jdbcQuery, Class<T> clazz) {
        return namedParameterJdbcTemplate.queryForObject(jdbcQuery.getQuerySql(), jdbcQuery.getArgsMap(), clazz);
    }

    /**
     * 更新
     */
    public int update(JdbcQuery jdbcQuery) {
        return namedParameterJdbcTemplate.update(jdbcQuery.getQuerySql(), jdbcQuery.getArgsMap());
    }

    /**
     * 单个对象查询
     *
     * @param jdbcQuery 查询对象
     * @param clazz     查询结果模型class对象
     */
    public <T> T queryObject(JdbcQuery jdbcQuery, Class<T> clazz) {
        return namedParameterJdbcTemplate.queryForObject(jdbcQuery.getQuerySql(), jdbcQuery.getArgsMap(), BeanPropertyRowMapper.newInstance(clazz));
    }

    /**
     * 单个对象查询
     *
     * @param sql   查询SQL
     * @param clazz 查询结果模型class对象
     */
    public <T> T queryObject(String sql, Class<T> clazz) {
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(clazz));
    }
}
