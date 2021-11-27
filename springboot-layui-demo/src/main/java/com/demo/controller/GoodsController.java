package com.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.demo.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2021/4/26 18:51
 * @Description TODO
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    // 注入实例化的对象，代替我们自己new
    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "/goodsList", method = RequestMethod.GET)
    public JSONObject getGoodList(int pageNum, int pageSize) {

        return goodsService.goodsList(pageNum, pageSize);
    }

    @RequestMapping(value = "updateGoods", method = RequestMethod.POST)
    public JSONObject updateGoods(@RequestBody JSONObject goods) {
        return goodsService.updateGoods(goods);
    }
}