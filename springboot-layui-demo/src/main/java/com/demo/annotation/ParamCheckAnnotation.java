package com.demo.annotation;

import java.lang.annotation.*;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2021/5/19 14:54
 * @Description TODO
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamCheckAnnotation {
}
