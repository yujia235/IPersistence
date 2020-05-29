package com.yujia.sqlsession;

import com.yujia.config.Configuration;

public interface SqlSessionFactory {
    SqlSession open();
}
