package com.demo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.demo.advice.BusinessErrorException;
import com.demo.advice.BusinessMsgEnum;
import com.demo.advice.ResponseInfo;
import com.demo.annotation.ParamCheckAnnotation;
import com.demo.dao.PersonDao;
import com.demo.model.Person;
import com.demo.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2021/4/23 14:45
 * @description TODO
 */
@Service
@Slf4j
public class PersonServiceImpl implements PersonService {
    @Resource
    private PersonDao personDao;

    // 太冗余，我们寻求一种简洁的处理方式
    // 统一的数据封装，我们预先定义好返回的数据格式，用得时候通过构造方法直接放入值即可
    @Override
    public ResponseInfo personsList(int pageNum, int pageSize) {

        try {
            PageHelper.startPage(pageNum, pageSize);
            PageInfo<Person> pageInfo = new PageInfo<>(personDao.getPersonsList());
            log.info("查询武侠列表成功--------->,{}", pageInfo.getList());
            // 通过有参构造方法直接对实例化的ResponseInfo进行初始化赋值
            return new ResponseInfo(pageInfo.getList(), pageInfo.getTotal());
        } catch (Exception e) {
            log.error("获取武侠列表失败--------->{}", e.getMessage());
            throw new BusinessErrorException(BusinessMsgEnum.ERROR_EXCEPTION);
        }
    }

    /**
     * 更新武侠
     *
     * @param person 武侠
     * @return
     */
    @Override
    @ParamCheckAnnotation()
    public ResponseInfo updatePerson(JSONObject person) {
        JSONObject result = new JSONObject();
        try {

            personDao.updatePerson(person);
            log.info("武侠信息更新成功---->{}", person);
            return new ResponseInfo(0, "success");
        } catch (Exception e) {

            log.error("更新武侠信息失败--------->{}", e.getMessage());
            return new ResponseInfo(500, "异常异常！");
        }
    }

    @Override
    @ParamCheckAnnotation()
    public ResponseInfo addPerson(JSONObject person) {
        try {

            personDao.addPerson(person);
            log.info("武侠信息新增成功---->{}", person);
            return new ResponseInfo(0, "success");
        } catch (Exception e) {
            log.error("武侠信息新增失败--------->{}", e.getMessage());
            return new ResponseInfo(500, "新增武侠异常！");
        }
    }

    @Override
    public JSONObject updatePersonBatch(JSONArray personArray) {
        JSONObject result = new JSONObject();
        try {
            result.put("code", "0");
            result.put("msg", "success");
            personDao.updatePersonBatch(personArray);
            log.info("武侠信息更新成功---->{}", personArray);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", "500");
            result.put("msg", "error");
            log.error("更新武侠信息失败--------->{}", e.getMessage());
        }
        return result;
    }

    @Override
    public ResponseInfo searchPerson(JSONObject query) {
        int pageNum = query.getInteger("pageNum");
        int pageSize = query.getInteger("pageSize");

        try {
            PageHelper.startPage(pageNum, pageSize);
            PageInfo<Person> pageInfo = new PageInfo<>(personDao.getPersonsList());
            List<Person> personList = personDao.searchPerson(query);
            log.info("武侠信息查询成功---->{}", personList);
            return new ResponseInfo(pageInfo.getList(), pageInfo.getTotal());
        } catch (Exception e) {
            log.error("武侠信息查询失败--------->{}", e.getMessage());
            return new ResponseInfo(500, "查询武侠信息异常！");
        }
    }

    @Override
    public ResponseInfo deletePersonById(Long id) {

        try {
            personDao.deletePersonById(id);
            log.info("武侠信息查询成功---->{}", id);
            return new ResponseInfo(0, "删除武侠成功!");
        } catch (Exception e) {
            log.error("武侠信息查询失败--------->{}", e.getMessage());
            return new ResponseInfo(500, "删除武侠异常！");
        }
    }
}
