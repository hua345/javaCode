package com.github.chenjianhua.common.mybatisplus.config;

import com.baomidou.mybatisplus.annotation.DbType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenjianhua
 * @date 2021/4/23
 */
@Setter
@Getter
@ToString
@Configuration
@ConfigurationProperties(prefix = "mybatis-plus")
public class MybatisPlusAutoProperties {

    private PageConfig pageConfig = new PageConfig();

    /**
     * 数据库类型,默认 mysql
     */
    private DbType dbType = DbType.MYSQL;

    @Getter
    @Setter
    public static class PageConfig {
        /**
         * 设置最大单页限制数量，默认 100 条，-1 不受限制
         */
        private long limit = 100L;
        /**
         * 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
         */
        private boolean overflow = false;
    }
}
