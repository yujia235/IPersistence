package com.yujia.sqlsession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;

public interface SqlSession {

    <T> T select(String mappedStatementId, Object ... params) throws Exception;

    <T> List<T> selectList(String mappedStatementId, Object ... params) throws Exception;

    /**
     * 获取Mapper代理对象
     * @param mapperClass
     * @param <T>
     * @return
     */
    default <T> T getMapper (Class<T> mapperClass) {
        SqlSession sqlSession = this;
        Method[] methods = sqlSession.getClass().getDeclaredMethods();
        Object proxy = Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 校验是否为接口中的方法
                if (Arrays.stream(methods).noneMatch(e1 -> e1.getName().equals(method.getName()))) {
                    return method.invoke(args);
                }
                String mappedStatementId = mapperClass.getName().concat(".").concat(method.getName());
                if (method.getGenericReturnType() instanceof ParameterizedType) {
                    return sqlSession.selectList(mappedStatementId, args);
                }
                return sqlSession.select(mappedStatementId, args);
            }
        });
        return (T) proxy;
    }
}
