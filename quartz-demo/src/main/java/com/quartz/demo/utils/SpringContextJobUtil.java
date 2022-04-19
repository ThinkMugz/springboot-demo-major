package com.quartz.demo.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2022/4/19 19:11
 * @description bean工具类
 */
@Component
public class SpringContextJobUtil implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    @SuppressWarnings("static-access")
    public void setApplicationContext(ApplicationContext contex) throws BeansException {
        this.context = contex;
    }

    /**
     * 根据name获取bean
     *
     * @param beanName name
     * @return bean对象
     */
    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    public static String getMessage(String key) {
        return context.getMessage(key, null, Locale.getDefault());
    }
}
