package com.quartz.demo.entity;

import lombok.Data;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2022/4/18 16:54
 * @description 人员表
 */
@Data
public class Person {
    private Long id;
    private String name;
    private Integer age;
    private String sex;
    private String address;
    private String sect;
    private String skill;
    private Integer power;
    private String createTime;
    private String modifyTime;
}
