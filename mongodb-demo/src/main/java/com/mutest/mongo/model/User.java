package com.mutest.mongo.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2022/8/30 14:21
 * @description 对应mongodb的实体类
 */
@Data
@Document("user")
public class User {
    private String id;
    private String username;
    private String password;
    private Integer age;
    private String city;
    private String createTime;
}
