package com.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.advice.ResponseInfo;
import com.demo.annotation.ParamCheckAnnotation;
import com.demo.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
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
@RequestMapping("/person")
@Slf4j
public class PersonController {
    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/personsList", method = RequestMethod.GET)
    public ResponseInfo getPersonList(int pageNum, int pageSize) {
        return personService.personsList(pageNum, pageSize);
    }

    @RequestMapping(value = "/updatePerson", method = RequestMethod.POST)
    @ParamCheckAnnotation()
    public ResponseInfo updatePerson(@RequestBody JSONObject person) {
        return personService.updatePerson(person);
    }

    @RequestMapping(value = "/updatePersonBatch", method = RequestMethod.POST)
    public JSONObject updatePersonBatch(@RequestBody JSONArray personArray) {
        return personService.updatePersonBatch(personArray);
    }

    @RequestMapping(value = "/addPerson", method = RequestMethod.POST)
    @ParamCheckAnnotation()
    public ResponseInfo addPerson(@RequestBody JSONObject person) {
        return personService.addPerson(person);
    }

    @RequestMapping(value = "/searchPerson", method = RequestMethod.POST)
    public ResponseInfo searchPerson(@RequestBody JSONObject person) {
        return personService.searchPerson(person);
    }

    @RequestMapping(value = "/deletePersonById", method = RequestMethod.GET)
    public ResponseInfo deletePersonById(@Param("id") Long id) {
        return personService.deletePersonById(id);
    }
}