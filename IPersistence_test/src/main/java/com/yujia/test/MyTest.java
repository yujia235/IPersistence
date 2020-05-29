package com.yujia.test;

import com.yujia.io.Resources;
import com.yujia.mapper.UserMapper;
import com.yujia.pojo.User;
import com.yujia.sqlsession.SqlSession;
import com.yujia.sqlsession.SqlSessionFactory;
import com.yujia.sqlsession.SqlSessionFactoryBuilder;

import java.util.List;

public class MyTest {

    public static void main(String[] args) throws Exception {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.load("sqlMapConfig.xml"));
        SqlSession sqlSession = sqlSessionFactory.open();

        System.out.println("-----------------普通方式--------------------");
        List<User> userList = sqlSession.selectList("com.yujia.mapper.UserMapper.selectList");
        for (User user0 : userList) {
            System.out.println("user0 = " + user0);
        }
        User user = new User();
        user.setUsername("tom");
        user.setPassword("123");
        User user1 = sqlSession.select("com.yujia.mapper.UserMapper.select", user);
        System.out.println("user1 = " + user1);
        // 新增
        user.setId(4);
        user.setUsername("lucy");
        int insert = sqlSession.insert("com.yujia.mapper.UserMapper.insert", user);
        System.out.println("insert = " + insert);
        // 修改
        user.setUsername("pob");
        int update = sqlSession.update("com.yujia.mapper.UserMapper.update", user);
        System.out.println("update = " + update);
        // 删除
        int delete = sqlSession.delete("com.yujia.mapper.UserMapper.delete", user);
        System.out.println("delete = " + delete);

        System.out.println();
        System.out.println();

        System.out.println("-----------------代理方式--------------------");
        UserMapper proxy = sqlSession.getMapper(UserMapper.class);
        List<User> userList1 = proxy.selectList();
        for (User user2 : userList1) {
            System.out.println("user2 = " + user2);
        }
        user.setUsername("tom");
        User user3 = proxy.select(user);
        System.out.println("user3 = " + user3);
        // 新增
        user = new User();
        user.setUsername("tom");
        user.setPassword("123");
        user.setId(4);
        user.setUsername("lucy");
        insert = proxy.insert(user);
        System.out.println("insert = " + insert);
        // 修改
        user.setUsername("pob");
        update = proxy.update( user);
        System.out.println("update = " + update);
        // 删除
        delete = proxy.delete(user);
        System.out.println("delete = " + delete);
    }
}
