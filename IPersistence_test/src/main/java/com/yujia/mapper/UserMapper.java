package com.yujia.mapper;

import com.yujia.pojo.User;

import java.util.List;

public interface UserMapper {
    User select(User user);
    List<User> selectList();
    int insert(User user);
    int delete(User user);
    int update(User user);
}
