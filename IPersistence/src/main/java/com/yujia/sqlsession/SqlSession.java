package com.yujia.sqlsession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface SqlSession {

    <T> T select(String mappedStatementId, Object ... params) throws Exception;

    <T> List<T> selectList(String mappedStatementId, Object ... params) throws Exception;

    int insert(String mappedStatementId, Object ... params) throws Exception;

    int delete(String mappedStatementId, Object ... params) throws Exception;

    int update(String mappedStatementId, Object ... params) throws Exception;

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
                List<Method> methodList = Arrays.stream(methods).filter(method1 -> method1.getName().equals(method.getName())).collect(Collectors.toList());
                if (methodList.isEmpty()) {
                    return method.invoke(args);
                }
                String mappedStatementId = mapperClass.getName().concat(".").concat(method.getName());
                return methodList.get(0).invoke(sqlSession, mappedStatementId, args);
            }
        });
        return (T) proxy;
    }
}
