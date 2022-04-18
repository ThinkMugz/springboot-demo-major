package com.quartz.demo.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2021/12/2 11:23
 * @description 定时任务工具方法
 */
@Slf4j
public class QuartzUtils {
    static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    static Scheduler scheduler;

    static {
        try {
            scheduler = schedulerFactory.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取定时任务状态
     *
     * @param triggerName      触发器名称
     * @param triggerGroupName 触发器组名
     * @return 状态名
     */
    public static String getTriggerState(String triggerName, String triggerGroupName) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            Trigger.TriggerState triggerState = scheduler.getTriggerState(triggerKey);
            return triggerState.name();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 自行传入任务和触发器方式启动一个任务
     *
     * @param jobDetail 任务详情
     * @param trigger   触发器
     */
    public static void startJob(JobDetail jobDetail, Trigger trigger) {
        try {
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
            System.out.println("当前触发器：" + scheduler.getTriggerGroupNames());
        } catch (SchedulerException e) {
            log.error("定时任务异常,jobDetail:{},e:{}", JSON.toJSON(jobDetail), e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 暂停触发器
     *
     * @param triggerName      triggerName
     * @param triggerGroupName triggerGroupName
     * @return true or false
     */
    public static boolean pauseTrigger(String triggerName, String triggerGroupName) {

        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            // 检查任务状态，只有运行态才允许暂停
            String stateName = getTriggerState(triggerName, triggerGroupName);
            if (StringUtils.isNull(stateName) || !stateName.equals("NORMAL")) {
                System.out.println("当前任务状态非法，state：" + stateName);
                return false;
            }
            scheduler.pauseTrigger(triggerKey);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 恢复触发器运行
     *
     * @param triggerName      triggerName
     * @param triggerGroupName triggerGroupName
     * @return true or false
     */
    public static boolean resumeTrigger(String triggerName, String triggerGroupName) {

        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            // 检查任务状态，只有暂停状态才允许恢复
            String stateName = getTriggerState(triggerName, triggerGroupName);
            if (StringUtils.isNull(stateName) || !stateName.equals("PAUSED")) {
                System.err.println("当前任务状态非法，state：" + stateName);
                return false;
            }
            scheduler.resumeTrigger(triggerKey);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 暂停job
     *
     * @param jobName      jobName
     * @param jobGroupName jobGroupName
     * @return true or false
     */
    public static boolean pauseJob(String jobName, String jobGroupName) {

        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            scheduler.pauseJob(jobKey);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 恢复job
     *
     * @param jobName      jobName
     * @param jobGroupName jobGroupName
     * @return true or false
     */
    public static boolean resumeJob(String jobName, String jobGroupName) {

        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            scheduler.resumeJob(jobKey);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 终止并删除触发器和任务
     *
     * @param jobName          任务名称
     * @param jobGroupName     任务组名称
     * @param triggerName      触发器名称
     * @param triggerGroupName 触发器组名称
     * @return true or false
     */
    public static boolean removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            Trigger trigger = scheduler.getTrigger(triggerKey);
            if (null == trigger) {
                return false;
            }
            scheduler.pauseTrigger(triggerKey); // 停止触发器
            scheduler.unscheduleJob(triggerKey); // 解除触发器
            scheduler.deleteJob(jobKey); // 删除任务
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 检查job是否存在scheduler中
     *
     * @param jobKey jobKey
     * @return true or false
     * @throws SchedulerException e
     */
    public static boolean checkExists(JobKey jobKey) throws SchedulerException {
        return scheduler.checkExists(jobKey);
    }

    /**
     * 检查trigger是否存在scheduler中
     *
     * @param triggerKey triggerKey
     * @return true or false
     * @throws SchedulerException e
     */
    public static boolean checkExists(TriggerKey triggerKey) throws SchedulerException {
        return scheduler.checkExists(triggerKey);
    }
}