package com.quartz.demo;

import com.quartz.demo.schedule.SimpleJob;
import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2022/4/12 21:02
 * @description 多定时任务测试
 */
public class MultiQuartzTest {

    @Test
    public void multiJobTest() throws SchedulerException, InterruptedException {
        // 1、创建Scheduler（调度器）
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        // 2、创建JobDetail实例，与执行内容类SimpleJob绑定，注意要设置 .storeDurably()，否则报错
        JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("job1", "job-group")
                .storeDurably()
                .build();

        // 3、分别构建Trigger实例
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "trigger-group")
                .startNow()//立即生效
                .forJob(jobDetail)
                .withSchedule(SimpleScheduleBuilder
                        .simpleSchedule()
                        .withIntervalInSeconds(2) //每隔3s执行一次
                        .repeatForever()) // 永久循环
                .build();
        Trigger trigger2 = TriggerBuilder.newTrigger().withIdentity("trigger2", "trigger-group")
                .startNow()//立即生效
                .forJob(jobDetail)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(3) //每隔5s执行一次
                        .repeatForever()).build(); // 永久循环
        //4、调度器中添加job
        scheduler.addJob(jobDetail, false);
        scheduler.scheduleJob(trigger);
        scheduler.scheduleJob(trigger2);
        // 启动调度器
        scheduler.start();
        // 休眠任务执行时长
        TimeUnit.SECONDS.sleep(20);
        scheduler.shutdown();
    }
}
