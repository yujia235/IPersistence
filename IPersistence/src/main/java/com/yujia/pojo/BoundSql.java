package com.yujia.pojo;

import com.yujia.parse.ParameterMapping;

import java.util.List;

public class BoundSql {
    private String sql;
    private List<ParameterMapping> parameterMappings;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public void setParameterMappings(List<ParameterMapping> parameterMappings) {
        this.parameterMappings = parameterMappings;
    }
}
