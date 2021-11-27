package com.demo.model;

import lombok.Data;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2021/4/23 13:48
 * @Description TODO
 */
@Data
public class Goods {
    private Long id;
    private String name;
    private String type;
    private double price;
    private int size;
    private String status;
    private String description;
    private String modifyTime;
    private String createTime;
}
