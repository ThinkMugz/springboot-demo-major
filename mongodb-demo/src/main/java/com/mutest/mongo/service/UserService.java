package com.mutest.mongo.service;

import com.mutest.mongo.model.User;

import java.util.List;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2022/8/30 15:58
 * @description 服务层
 */
public interface UserService {
    void saveUser(User user);

    List<User> queryList(User user);
}
