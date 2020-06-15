package com.yujia.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 数据源
 * 为防止数据源被外界误修改，故数据源不对外暴露，仅提供获取连接的方法
 */
public class DruidUtils {

    private static DruidDataSource druidDataSource = new DruidDataSource();

    private DruidUtils() {
    }

    static {
        druidDataSource.setUrl("jdbc:mysql:///0db");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("root");
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
    }

    public static Connection getConnection () throws SQLException {
        return druidDataSource.getConnection();
    }
}
