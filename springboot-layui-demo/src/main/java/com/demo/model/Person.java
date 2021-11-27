package com.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2021/4/23 13:48
 * @Description TODO
 */
@Data
@Builder
@AllArgsConstructor
public class Person {
    private Long id;
    private String name;
    private Long age;
    private String sex;
    private String address;
    private String sect;
    private String skill;
    private int power;
    private String modifyTime;
    private String createTime;

    public Person() {
    }
}
