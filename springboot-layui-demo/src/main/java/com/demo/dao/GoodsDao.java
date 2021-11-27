package com.demo.dao;

import com.alibaba.fastjson.JSONObject;
import com.demo.model.Goods;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2021/4/23 14:30
 * @Description TODO
 */
@Mapper
public interface GoodsDao {
    @Select("SELECT * FROM mytest.goods")
    List<Goods> getGoodsList();

    @Update("UPDATE mytest.goods SET name=#{name},type=#{type},status=#{status},price=#{price},size=#{size},status=#{status},description=#{description} WHERE id=#{id}")
    int updateGoods(JSONObject goods);
}
