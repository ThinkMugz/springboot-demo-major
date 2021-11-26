package com.aop.annotation;

import java.lang.annotation.*;

/**
 * @author guozhengMu
 * @version 1.0
 * @createTime 2020/10/16 11:17
 * @description
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionAnnotation {
}
