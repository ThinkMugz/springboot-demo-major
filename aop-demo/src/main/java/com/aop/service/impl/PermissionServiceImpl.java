package com.aop.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aop.annotation.PermissionAnnotation;
import com.aop.service.PermissionService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2021/11/26 15:06
 * @description TODO
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    // todo 用这个注解修饰的方法（也可以修饰类），将被PermissionAdvice切入处理
    @PermissionAnnotation()
    @Override
    public JSONObject getGroupList(@RequestBody JSONObject request) {
        System.out.println("原始请求信息：" + request);
        return JSON.parseObject("{\"message\":\"SUCCESS\",\"code\":200}");
    }
}
