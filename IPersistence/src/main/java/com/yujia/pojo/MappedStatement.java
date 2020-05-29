package com.yujia.pojo;

public class MappedStatement {
    private String id;
    private String sql;
    private Class<?> paramterClass;
    private Class<?> resultClass;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Class<?> getParamterClass() {
        return paramterClass;
    }

    public void setParamterClass(Class<?> paramterClass) {
        this.paramterClass = paramterClass;
    }

    public Class<?> getResultClass() {
        return resultClass;
    }

    public void setResultClass(Class<?> resultClass) {
        this.resultClass = resultClass;
    }
}
