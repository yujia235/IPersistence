package com.yujia.utils;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.yujia.annotation.Component;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 连接
 */
@Component("connectionUtils")
public class ConnectionUtils {

    private final ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();

    public Connection getConnection() throws SQLException {
        Connection connection = connectionThreadLocal.get();
        if (connection == null) {
            connection = DruidUtils.getConnection();
            connectionThreadLocal.set(connection);
        }
        return connection;
    }
}
