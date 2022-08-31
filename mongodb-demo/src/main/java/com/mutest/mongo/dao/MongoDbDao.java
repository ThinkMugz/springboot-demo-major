package com.mutest.mongo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2022/8/30 15:40
 * @description 针对任意实体类的通用封装
 */
public abstract class MongoDbDao<T> {

    /**
     * 反射获取泛型类型
     *
     * @return Class
     */
    protected abstract Class<T> getEntityClass();

    @Autowired
    private MongoTemplate mongoTemplate;

    /***
     * 保存一个对象
     * @param t class对象
     */
    public void save(T t) {
        this.mongoTemplate.save(t);
    }

    /***
     * 根据id从几何中查询对象
     * @param id id
     * @return 查询结果
     */
    public T queryById(Integer id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return this.mongoTemplate.findOne(query, this.getEntityClass());
    }

    /**
     * 根据条件查询集合
     *
     * @param object 查询条件
     * @return 查询结果
     */
    public List<T> queryList(T object) {
        Query query = getQueryByObject(object);
        return mongoTemplate.find(query, this.getEntityClass());
    }

    /**
     * 根据条件查询只返回一个文档
     *
     * @param object 查询条件
     * @return 查询结果
     */
    public T queryOne(T object) {
        Query query = getQueryByObject(object);
        return mongoTemplate.findOne(query, this.getEntityClass());
    }

    /**
     * 根据条件分页查询
     *
     * @param object 查询条件
     * @param start  查询起始值
     * @param size   查询大小
     * @return 查询结果
     */
    public List<T> getPage(T object, int start, int size) {
        Query query = getQueryByObject(object);
        query.skip(start);
        query.limit(size);
        return this.mongoTemplate.find(query, this.getEntityClass());
    }

    /**
     * 根据条件查询库中符合条件的记录数量
     *
     * @param object 查询条件
     * @return 查询结果
     */
    public Long getCount(T object) {
        Query query = getQueryByObject(object);
        return this.mongoTemplate.count(query, this.getEntityClass());
    }

    /**
     * 删除对象
     *
     * @param t 对象
     * @return 删除数
     */
    public int delete(T t) {
        return (int) this.mongoTemplate.remove(t).getDeletedCount();
    }

    /**
     * 根据id删除
     *
     * @param id id
     */
    public void deleteById(Integer id) {
        Criteria criteria = Criteria.where("_id").is(id);
        Query query = new Query(criteria);
        T obj = this.mongoTemplate.findOne(query, this.getEntityClass());
        if (obj != null) {
            this.delete(obj);
        }
    }

    /*MongoDB中更新操作分为三种
     * 1：updateFirst     修改第一条
     * 2：updateMulti     修改所有匹配的记录
     * 3：upsert  修改时如果不存在则进行添加操作
     * */

    /**
     * 修改匹配到的第一条记录
     *
     * @param srcObj    查询条件
     * @param targetObj 匹配条件
     */
    public void updateFirst(T srcObj, T targetObj) {
        Query query = getQueryByObject(srcObj);
        Update update = getUpdateByObject(targetObj);
        this.mongoTemplate.updateFirst(query, update, this.getEntityClass());
    }

    /**
     * 修改匹配到的所有记录
     *
     * @param srcObj    查询条件
     * @param targetObj 匹配条件
     */
    public void updateMulti(T srcObj, T targetObj) {
        Query query = getQueryByObject(srcObj);
        Update update = getUpdateByObject(targetObj);
        this.mongoTemplate.updateMulti(query, update, this.getEntityClass());
    }

    /**
     * 修改匹配到的记录，若不存在该记录则进行添加
     *
     * @param srcObj    查询条件
     * @param targetObj 匹配条件
     */
    public void updateInsert(T srcObj, T targetObj) {
        Query query = getQueryByObject(srcObj);
        Update update = getUpdateByObject(targetObj);
        this.mongoTemplate.upsert(query, update, this.getEntityClass());
    }

    /**
     * 将查询条件对象转换为query
     *
     * @param object 对象
     * @return Query
     */
    private Query getQueryByObject(T object) {
        Query query = new Query();
        String[] fields = getFieldName(object);
        Criteria criteria = new Criteria();
        for (String field : fields) {
            Object fieldValue = getFieldValueByName(field, object);
            if (fieldValue != null) {
                criteria.and(field).is(fieldValue);
            }
        }
        query.addCriteria(criteria);
        return query;
    }

    /**
     * 将查询条件对象转换为update
     *
     * @param object 查询条件对象
     * @return update
     */
    private Update getUpdateByObject(T object) {
        Update update = new Update();
        String[] fields = getFieldName(object);
        for (String field : fields) {
            Object fieldValue = getFieldValueByName(field, object);
            if (fieldValue != null) {
                update.set(field, fieldValue);
            }
        }
        return update;
    }

    /***
     * 获取对象属性返回字符串数组
     * @param o 对象
     * @return 字段数组
     */
    private static String[] getFieldName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];

        for (int i = 0; i < fields.length; ++i) {
            fieldNames[i] = fields[i].getName();
        }

        return fieldNames;
    }

    /**
     * 根据属性获取对象属性值
     *
     * @param fieldName 字段名称
     * @param o         对象
     * @return 属性值
     */
    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String e = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + e + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter);
            return method.invoke(o);
        } catch (Exception var6) {
            return null;
        }
    }
}