package com.aop.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2021/11/26 15:05
 * @description TODO
 */
@Service
public interface PermissionService {
    JSONObject getGroupList(@RequestBody JSONObject request);
}
