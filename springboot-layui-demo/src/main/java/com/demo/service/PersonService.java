package com.demo.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.advice.ResponseInfo;
import org.springframework.stereotype.Service;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2021/4/23 14:45
 * @Description TODO
 */
@Service
public interface PersonService {
    ResponseInfo personsList(int pageNum, int pageSize);

    ResponseInfo updatePerson(JSONObject person);

    ResponseInfo addPerson(JSONObject person);

    JSONObject updatePersonBatch(JSONArray personArray);

    ResponseInfo searchPerson(JSONObject person);

    ResponseInfo deletePersonById(Long id);
}
