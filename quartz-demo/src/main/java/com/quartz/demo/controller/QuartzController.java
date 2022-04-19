package com.quartz.demo.controller;

import com.quartz.demo.mapper.PersonMapper;
import com.quartz.demo.schedule.MajorJob;
import com.quartz.demo.utils.QuartzUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2022/4/13 15:56
 * @description 定时任务控制层
 */
@RestController
@RequestMapping("/quartz")
public class QuartzController {
    @Autowired
    private PersonMapper personMapper;

    static String jobName = "jobName-1";
    static String triggerName = "triggerName-1";
    static String jobGroupName = "job-group";
    static String triggerGroupName = "trigger-group";

    /**
     * 任务启动
     *
     * @return string
     */
    @RequestMapping(value = "/trigger/start", method = RequestMethod.GET)
    public String startJob() {
        // 构建定时任务
        JobDetail jobDetail = JobBuilder.newJob(MajorJob.class)
                .withIdentity(jobName, jobGroupName)
                .usingJobData("jobName", "QuartzDemo")
                .build();
        // 将mapper放入jobDetail的jobDataMap中
        // jobDetail.getJobDataMap().put("personMapper", personMapper);

        Date start = new Date();
        start.setTime(start.getTime() + 10000);
        Date end = new Date();
        end.setTime(end.getTime() + 90000);

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerName, triggerGroupName)
                .startNow()  // 定义开始时间
                .endAt(end)  // 定义结束时间
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(10, 10))
                .build();
        QuartzUtils.startJob(jobDetail, trigger);
        return "启动成功！";
    }

    /**
     * 暂停触发器
     *
     * @return string
     */
    @RequestMapping(value = "/trigger/pause", method = RequestMethod.GET)
    public String pauseTrigger() {
        QuartzUtils.pauseTrigger(triggerName, triggerGroupName);
        return "Trigger暂停成功！";
    }

    /**
     * 恢复触发器
     *
     * @return string
     */
    @RequestMapping(value = "/trigger/resume", method = RequestMethod.GET)
    public String resumeTrigger() {
        QuartzUtils.resumeTrigger(triggerName, triggerGroupName);
        return "Trigger重启成功！";
    }

    /**
     * 关闭触发器
     *
     * @return string
     */
    @RequestMapping(value = "/trigger/shutdown", method = RequestMethod.GET)
    public String shutdown() {
        QuartzUtils.removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
        return "停止成功";
    }

    /**
     * 查询触发器状态
     *
     * @return string
     */
    @RequestMapping(value = "/trigger/state", method = RequestMethod.GET)
    public String getTriggerState() {
        return QuartzUtils.getTriggerState(triggerName, triggerGroupName);
    }

    /**
     * 查询触发器是否存在
     *
     * @return true of false
     * @throws SchedulerException e
     */
    @RequestMapping(value = "/trigger/exists", method = RequestMethod.GET)
    public boolean checkTriggerExists() throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
        return QuartzUtils.checkExists(triggerKey);
    }

    /**
     * 暂停job
     *
     * @return string
     */
    @RequestMapping(value = "/job/pause", method = RequestMethod.GET)
    public String pauseJob() {
        QuartzUtils.pauseJob(jobName, jobGroupName);
        return "JOB暂停成功！";
    }

    /**
     * 恢复job
     *
     * @return string
     */
    @RequestMapping(value = "/job/resume", method = RequestMethod.GET)
    public String resumeJob() {
        QuartzUtils.resumeJob(jobName, jobGroupName);
        return "JOB恢复成功！";
    }

    /**
     * 查询job是否存在
     *
     * @return true or false
     * @throws SchedulerException e
     */
    @RequestMapping(value = "/job/exists", method = RequestMethod.GET)
    public boolean checkJobExists() throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
        return QuartzUtils.checkExists(jobKey);
    }
}
