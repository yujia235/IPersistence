package com.yujia.execut;

import com.yujia.config.Configuration;
import com.yujia.parse.GenericTokenParser;
import com.yujia.parse.ParameterMapping;
import com.yujia.parse.ParameterMappingTokenHandler;
import com.yujia.parse.TokenHandler;
import com.yujia.pojo.BoundSql;
import com.yujia.pojo.MappedStatement;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleExecutor implements Executor {

    @Override
    public <T> List<T> query(Configuration configuration, MappedStatement mappedStatement, Object[] param) throws Exception {
        // 获取连接
        Connection connection = configuration.getDataSource().getConnection();
        // sql解析
        BoundSql boundSql = getBoundSql(mappedStatement.getSql());
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSql());

        Class<?> paramterClass = mappedStatement.getParamterClass();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        Field declaredField = null;
        for (int i = 0; i < parameterMappings.size(); i++) {
            declaredField = paramterClass.getDeclaredField(parameterMappings.get(i).getContent());
            declaredField.setAccessible(true);
            // 参数
            preparedStatement.setObject(i+1, declaredField.get(param[0]));
        }
        // 执行
        ResultSet resultSet = preparedStatement.executeQuery();
        // 封装
        return parseResult(resultSet, mappedStatement.getResultClass());
    }

    private <T> List<T> parseResult(ResultSet resultSet, Class resultClass) throws Exception {
        List<T> list = new ArrayList<>();
        T t = null;
        ResultSetMetaData metaData = null;
        while (resultSet.next()) {
            t = (T) resultClass.newInstance();
            metaData = resultSet.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                String name = metaData.getColumnName(i);
                // 属性描述器，为属性生成读写方法
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(name, resultClass);
                // 写入值
                propertyDescriptor.getWriteMethod().invoke(t, resultSet.getObject(name));
            }
            list.add(t);
        }
        return list;
    }

    private BoundSql getBoundSql(String sql) {
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        String parse = genericTokenParser.parse(sql);
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        BoundSql boundSql = new BoundSql();
        boundSql.setSql(parse);
        boundSql.setParameterMappings(parameterMappings);
        return boundSql;
    }
}
