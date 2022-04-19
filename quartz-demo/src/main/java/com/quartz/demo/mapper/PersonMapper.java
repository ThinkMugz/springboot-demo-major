package com.quartz.demo.mapper;

import com.quartz.demo.entity.Person;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2022/4/18 16:57
 * @description person数据层
 */
@Service("personMapper")
public interface PersonMapper {
    @Select("select id,name,age,sex,address,sect,skill,power,create_time createTime,modify_time modifyTime from mytest.persons")
    List<Person> queryList();
}
