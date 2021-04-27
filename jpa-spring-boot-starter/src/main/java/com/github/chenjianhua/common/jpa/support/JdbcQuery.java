package com.github.chenjianhua.common.jpa.support;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenjianhua
 * @date 2020/9/7
 */
@Setter
@Getter
@ToString
public class JdbcQuery {

    private String querySql;

    private Map<String, Object> argsMap = new HashMap<>();

    public JdbcQuery(String querySql, Map<String, Object> argsMap){
        this.querySql = querySql;
        if(argsMap != null) {
            this.argsMap.putAll(argsMap);
        }
    }

    public static JdbcQuery of(String querySql, Map<String, Object> argsMap){
        return new JdbcQuery(querySql, argsMap);
    }

    public static JdbcQuery of(String querySql){
        return of(querySql, null);
    }

    public JdbcQuery singletonMap(String key, Object value) {
        this.argsMap.put(key, value);
        return this;
    }
}

