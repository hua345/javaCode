package com.github.chenjianhua.common.druid.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author chenjianhua
 * @date 2020-09-07 15:41:49
 * https://github.com/alibaba/druid/wiki/DruidDataSource%E9%85%8D%E7%BD%AE%E5%B1%9E%E6%80%A7%E5%88%97%E8%A1%A8
 */
@Data
@ConfigurationProperties(prefix = "spring.datasource.druid")
public class DruidDataSourceProperties {
    /**
     * 初始化时建立物理连接的个数。默认0
     */
    private int initialSize = 5;
    /**
     * 最小连接池数量
     */
    private int minIdle = 5;
    /**
     * 最大连接池数量,默认8
     */
    private int maxActive = 30;
    /**
     * 获取连接时最大等待时间，单位毫秒。配置了maxWait之后，缺省启用公平锁，并发效率会有所下降
     */
    private long maxWait = 60000L;
    /**
     * 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
     */
    private long timeBetweenEvictionRunsMillis = 6 * 1000L;
    /**
     * 连接保持空闲而不被驱逐的最小时间，单位是毫秒
     */
    private long minEvictableIdleTimeMillis = 300 * 1000L;
    /**
     * 用来检测连接是否有效的sql，要求是一个查询语句，常用select 'x'。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会起作用。
     */
    private String validationQuery = "SELECT 1 FROM DUAL";
    /**
     * 默认false,建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
     */
    private boolean testWhileIdle = true;
    /**
     * 默认true,申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
     */
    private boolean testOnBorrow = false;
    /**
     * 默认false,归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
     */
    private boolean testOnReturn = true;
    /**
     * 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。
     */
    private boolean poolPreparedStatements = false;
    /**
     * 要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100
     */
    private int maxPoolPreparedStatementPerConnectionSize = 20;
    /**
     * 配置监控统计拦截的filters，去掉后监控界面sql无法统计，
     * Druid内置提供一个StatFilter，用于统计监控信息
     * wall用于防火墙（防止SQL注入）
     * slf4j日志打印
     */
    private String filters = "stat,wall,slf4j";
    /**
     * 通过connectProperties属性来打开mergeSql功能；慢SQL记录
     * StatFilter属性slowSqlMillis用来配置SQL慢的标准，执行时间超过slowSqlMillis的就是慢。
     * slowSqlMillis的缺省值为3000，也就是3秒。
     */
    private String connectionProperties = "druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500";
    /**
     * 合并多个DruidDataSource的监控数据
     */
    private boolean useGlobalDataSourceStat = true;
}