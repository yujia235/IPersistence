package com.yujia.sqlsession;

import com.yujia.config.Configuration;
import com.yujia.execut.Executor;
import com.yujia.execut.SimpleExecutor;

import java.util.List;

public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    // 执行器
    private Executor excutor;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        this.excutor = new SimpleExecutor();
    }

    @Override
    public <T> T select(String mappedStatementId, Object... params) throws Exception {
        List<T> list = selectList(mappedStatementId, params);
        if (list == null) {
            return null;
        }
        if (list.size() == 1) {
            return list.get(0);
        } else {
            throw new RuntimeException("有多条结果");
        }
    }

    @Override
    public <T> List<T> selectList(String mappedStatementId, Object... params) throws Exception {
        return (List<T>) excutor.query(configuration, configuration.getMappedStatementMap().get(mappedStatementId), params);
    }
}
