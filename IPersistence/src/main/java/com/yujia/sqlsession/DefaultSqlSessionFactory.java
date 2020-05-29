package com.yujia.sqlsession;

import com.yujia.config.Configuration;

public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession open() {
        return new DefaultSqlSession(configuration);
    }
}
