package com.yujia.sqlsession;

import com.yujia.config.Configuration;
import com.yujia.config.XMLConfigerBuilder;

import java.io.InputStream;

public class SqlSessionFactoryBuilder {

    private Configuration configuration;

    public SqlSessionFactoryBuilder() {
        this.configuration = new Configuration();
    }

    public SqlSessionFactory build(InputStream inputstream) throws Exception {
        new XMLConfigerBuilder(configuration).parseConfiguration(inputstream);
        return new DefaultSqlSessionFactory(configuration);
    }
}
