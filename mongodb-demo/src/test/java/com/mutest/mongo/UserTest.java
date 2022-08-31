package com.mutest.mongo;

import com.mutest.mongo.model.User;
import com.mutest.mongo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2022/8/30 16:08
 * @description mongo测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MongoApplication.class)
public class UserTest {
    @Autowired
    private UserService userService;

    @Test
    public void insertUserTest() {
        User user = new User();
        user.setId("522345");
        user.setUsername("万楚良");
        user.setPassword("12342345");
        user.setAge(19);
        user.setCity("北京市");
        user.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        userService.saveUser(user);
    }

    @Test
    public void queryListTest() {
        User user = new User();
        user.setCity("北京市");
        user.setAge(19);

        System.out.println(userService.queryList(user));
    }
}
