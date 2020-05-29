package com.yujia.execut;

import com.yujia.config.Configuration;
import com.yujia.pojo.MappedStatement;

import java.sql.SQLException;
import java.util.List;

public interface Executor {


    <T> List<T> query(Configuration configuration, MappedStatement mappedStatement, Object[] param) throws Exception;
}
