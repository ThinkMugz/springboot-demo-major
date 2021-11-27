package com.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.demo.dao.GoodsDao;
import com.demo.model.Goods;
import com.demo.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2021/4/23 14:45
 * @Description TODO
 */
@Service
@Slf4j
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsDao goodDao;

    @Override
    public JSONObject goodsList(int pageNum, int pageSize) {
        JSONObject result = new JSONObject();
        try {
            result.put("code", "200");
            result.put("msg", "chengong");
            PageHelper.startPage(pageNum, pageSize);
            PageInfo<Goods> pageInfo = new PageInfo<>(goodDao.getGoodsList());
            result.put("data", pageInfo.getList());
            log.info("查询商品成功--------->,{}", pageInfo.getList());
        } catch (Exception e) {
            result.put("code", "500");
            result.put("msg", "error");
            log.error("获取商品列表失败--------->{}", e.getMessage());
        }
        return result;
    }

    /**
     * 更新商品
     *
     * @param goods 商品
     * @return
     */
    public JSONObject updateGoods(JSONObject goods) {
        JSONObject result = new JSONObject();
        try {
            result.put("code", "0");
            result.put("msg", "success");
            goodDao.updateGoods(goods);
            log.info("商品更新成功---->{}", goods);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", "500");
            result.put("msg", "error");
            log.error("更新商品失败--------->{}", e.getMessage());
        }
        return result;
    }
}
