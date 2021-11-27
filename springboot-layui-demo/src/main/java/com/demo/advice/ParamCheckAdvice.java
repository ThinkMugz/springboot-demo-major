package com.demo.advice;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2021/5/19 14:56
 * @Description TODO
 */

/**
 * @Aspect 表示该类是一个AOP处理类
 * @Component 表示该类作为一个组件交给Spring容器管理
 */
@Aspect
@Component
public class ParamCheckAdvice {
    /**
     * 定义一个切面，拦截所有被ParamCheckAnnotation注解修饰的方法
     */
    @Pointcut("@annotation(com.demo.annotation.ParamCheckAnnotation)")
    private void paramCheck() {
    }

    @Around("paramCheck()")  //Before  After
    public ResponseInfo paramCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("===================第一个切面===================：" + System.currentTimeMillis());

        // 拿到请求参数
        Object[] objects = joinPoint.getArgs();
        // 从参数中解析出age
        Long age = ((JSONObject) objects[0]).getLong("age");
        System.out.println("age->>>>>>>>>>>>>>>>>>>>>>" + age);

        // 年龄小于13则返回异常
        if (age < 3) {
            return new ResponseInfo(403,"年龄太小莫入江湖");
        }
        return (ResponseInfo) joinPoint.proceed();
    }
}
