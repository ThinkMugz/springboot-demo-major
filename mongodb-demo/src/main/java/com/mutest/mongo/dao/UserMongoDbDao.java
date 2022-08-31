package com.mutest.mongo.dao;

import com.mutest.mongo.model.User;
import org.springframework.stereotype.Repository;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2022/8/30 16:00
 * @description 具体类的mongo操作
 */
@Repository
public class UserMongoDbDao extends MongoDbDao<User> {
    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }
}
