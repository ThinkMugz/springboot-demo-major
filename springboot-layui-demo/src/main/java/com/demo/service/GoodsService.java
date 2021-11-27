package com.demo.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2021/4/23 14:45
 * @Description TODO
 */
@Service
public interface GoodsService {
    JSONObject goodsList(int pageNum, int pageSize);

    JSONObject updateGoods(JSONObject goods);
}
