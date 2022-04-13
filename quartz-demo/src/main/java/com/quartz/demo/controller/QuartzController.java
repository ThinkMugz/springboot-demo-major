package com.quartz.demo.controller;

import com.quartz.demo.schedule.SimpleJob;
import com.quartz.demo.utils.QuartzUtils;
import org.quartz.*;
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
    static String jobName = "jobName-1";
    static String triggerName = "triggerName-1";
    static String jobGroupName = "job-group";
    static String triggerGroupName = "trigger-group";

    @RequestMapping(value = "/trigger/start", method = RequestMethod.GET)
    public String startJob() {
        // 构建定时任务
        JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class)
                .withIdentity(jobName, jobGroupName).build();

        Date end = new Date();
        end.setTime(end.getTime() + 30000); // 持续30s

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerName, triggerGroupName)
                .startNow()
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(2))
                .endAt(end)  // 定义结束时间
                .build();
        QuartzUtils.startJob(jobDetail, trigger);
        return "启动成功！";
    }

    @RequestMapping(value = "/trigger/pause", method = RequestMethod.GET)
    public String pauseTrigger() {
        QuartzUtils.pauseTrigger(triggerName, triggerGroupName);
        return "暂停成功！";
    }

    @RequestMapping(value = "/trigger/resume", method = RequestMethod.GET)
    public String resumeTrigger() {
        QuartzUtils.resumeTrigger(triggerName, triggerGroupName);
        return "重启成功！";
    }

    @RequestMapping(value = "/trigger/shutdown", method = RequestMethod.GET)
    public String shutdown() {
        QuartzUtils.removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
        return "停止成功";
    }

    @RequestMapping(value = "/trigger/state", method = RequestMethod.GET)
    public String getTriggerState() {
        return QuartzUtils.getTriggerState(triggerName, triggerGroupName);
    }

    @RequestMapping(value = "/trigger/exists", method = RequestMethod.GET)
    public boolean checkTriggerExists() throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
        return QuartzUtils.checkExists(triggerKey);
    }


    @RequestMapping(value = "/job/exists", method = RequestMethod.GET)
    public boolean checkJobExists() throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
        return QuartzUtils.checkExists(jobKey);
    }
}
