package com.quartz.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.quartz.demo.mapper")
public class QuartzDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuartzDemoApplication.class, args);
    }

}
