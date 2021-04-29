# druid starter

- [https://github.com/alibaba/druid](https://github.com/alibaba/druid)
- [https://github.com/alibaba/druid/wiki/DruidDataSource配置属性列表](https://github.com/alibaba/druid/wiki/DruidDataSource%E9%85%8D%E7%BD%AE%E5%B1%9E%E6%80%A7%E5%88%97%E8%A1%A8)

## 功能点

- druid自动化配置,引入的项目无需额外配置
- 浏览器打开`/druid/index.html`,账号默认admin

## maven引入

```xml

<dependency>
    <groupId>com.github.chenjianhua.common</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.2.6</version>
</dependency>
```

## 配置数据库连接

```properties
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/db_example?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Asia/Shanghai
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
```