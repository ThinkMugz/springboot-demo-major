package com.quartz.demo;

import com.quartz.demo.schedule.SimpleJob;
import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2022/4/12 20:34
 * @description 简单定时任务测试
 */
public class SimpleQuartzTest {
    /*
     * 基于时间间隔的定时任务
     */
    @Test
    public void simpleTest() throws SchedulerException, InterruptedException {
        // 1、创建Scheduler（调度器）
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        // 2、创建JobDetail实例，并与SimpleJob类绑定(Job执行内容)
        JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("job-1", "job-group")
                .build();
        // 3、构建Trigger（触发器），定义执行频率和时长
        Trigger trigger = TriggerBuilder.newTrigger()
                // 指定group和name，这是唯一身份标识
                .withIdentity("trigger-1", "trigger-group")
                .startNow()  //立即生效
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(2) //每隔2s执行一次
                        .repeatForever())  // 永久执行
                .build();
        //4、将Job和Trigger交给Scheduler调度
        scheduler.scheduleJob(jobDetail, trigger);
        // 5、启动Scheduler
        scheduler.start();
        // 休眠，决定调度器运行时间，这里设置30s
        TimeUnit.SECONDS.sleep(30);
        // 关闭Scheduler
        scheduler.shutdown();
    }

    /*
     * 基于cron表达式的定时任务
     */
    @Test
    public void cronTest() throws SchedulerException, InterruptedException {
        // 1、创建Scheduler（调度器）
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        // 2、创建JobDetail实例，并与SimpleJob类绑定
        JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("job-1", "job-group").build();
        // 3、构建Trigger（触发器），定义执行频率和时长
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger-1", "trigger-group")
                .startNow()  //立即生效
                .withSchedule(CronScheduleBuilder.cronSchedule("* 30 10 ? * 1/5 *"))
                .build();

        //4、执行
        scheduler.scheduleJob(jobDetail, cronTrigger);
        scheduler.start();
        // 休眠，决定调度器运行时间，这里设置30s
        TimeUnit.SECONDS.sleep(30);
        // 关闭Scheduler
        scheduler.shutdown();
    }
}
