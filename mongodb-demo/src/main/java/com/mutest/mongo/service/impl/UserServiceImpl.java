package com.mutest.mongo.service.impl;

import com.mutest.mongo.dao.UserMongoDbDao;
import com.mutest.mongo.model.User;
import com.mutest.mongo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2022/8/30 15:59
 * @description mongo测试类
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMongoDbDao userMongoDbDao;

    @Override
    public void saveUser(User user) {
        userMongoDbDao.save(user);
    }

    @Override
    public List<User> queryList(User user) {
        return userMongoDbDao.queryList(user);
    }
}
