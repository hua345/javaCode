# mybatis springboot starter

- [https://github.com/mybatis/mybatis-3](https://github.com/mybatis/mybatis-3)

## 配置

```yaml
#pagehelper
# 分页插件
pagehelper:
    helperDialect: mysql
    reasonable: false
    params: count=countSql
    supportMethodsArguments: true
```

## 使用

使用PageHelper的api分页
PageHelper.startPage(pageCount, pageSize);
