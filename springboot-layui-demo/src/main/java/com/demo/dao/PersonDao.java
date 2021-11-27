package com.demo.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.model.Person;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2021/4/23 14:30
 * @Description TODO
 */
public interface PersonDao {
    List<Person> getPersonsList();

    int updatePerson(JSONObject person);

    int updatePersonBatch(JSONArray personArray);

    int addPerson(JSONObject person);

    List<Person> searchPerson(JSONObject person);

    @Delete("DELETE FROM mytest.persons where id = #{id}")
    int deletePersonById(@Param("id") Long id);
}
