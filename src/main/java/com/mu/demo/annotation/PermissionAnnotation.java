package com.mu.demo.annotation;

import java.lang.annotation.*;

/**
 * @author guozhengMu
 * @version 1.0
 * @date 2020/10/16 11:17
 * @description
 * @modify
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionAnnotation {
}
