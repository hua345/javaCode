# redis starter

## 功能点

- 自动化配置使用`Lettuce`作为`redis连接池`
- 自动化配置`jackson`作为对象序列化方法
- 实现了`redis锁`功能
- 提供`RedisStringTemplate`作为常用key value操作

## maven引入

```xml

<dependency>
    <groupId>com.github.chenjianhua.common</groupId>
    <artifactId>redis-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```