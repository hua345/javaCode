package com.github.chenjianhua.common.mybatis.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author chenjianhua
 * @date 2021/4/27
 */
@Configuration
public class PageHelperConfig {
    /**
     * helperDialect：指定数据库方言
     * reasonable：默认是false。启用合理化时，如果pageNum<1会查询第一页，如果pageNum>pages（最大页数）会查询最后一页。禁用合理化时，如果pageNum<1或pageNum>pages会返回空数据
     * supportMethodsArguments：是否支持接口参数来传递分页参数，默认false
     * params：为了支持startPage(Object params)方法，增加了该参数来配置参数映射，用于从对象中根据属性名取值， 可以配置 pageNum,pageSize,count,pageSizeZero,reasonable，不配置映射的用默认值， 默认值为pageNum=pageNum;pageSize=pageSize;count=countSql;reasonable=reasonable;pageSizeZero=pageSizeZero
     * 其余参数详情请看官方文档：https://pagehelper.github.io/docs/howtouse/
     * https://github.com/pagehelper/Mybatis-PageHelper
     */
    @Bean
    public PageHelper getPageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }
}
