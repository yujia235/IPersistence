package com.yujia.execut;

import com.yujia.config.Configuration;
import com.yujia.pojo.MappedStatement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class SimpleExecutor implements Executor {

    @Override
    public <T> List<T> executeQuery(Configuration configuration, MappedStatement mappedStatement, Object[] param) throws Exception {
        // 准备
        PreparedStatement preparedStatement =  getpreparedStatement(configuration, mappedStatement, param);
        // 执行
        ResultSet resultSet = preparedStatement.executeQuery();
        // 封装
        return parseResult(resultSet, mappedStatement.getResultClass());
    }

    @Override
    public int executeUpdate(Configuration configuration, MappedStatement mappedStatement, Object[] param) throws Exception {
        // 准备
        PreparedStatement preparedStatement =  getpreparedStatement(configuration, mappedStatement, param);
        // 执行
        return preparedStatement.executeUpdate();
    }
}
