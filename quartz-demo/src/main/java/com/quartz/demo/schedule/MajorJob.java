package com.quartz.demo.schedule;

import com.quartz.demo.entity.Person;
import com.quartz.demo.mapper.PersonMapper;
import com.quartz.demo.utils.SpringContextJobUtil;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2022/4/18 16:47
 * @description 更复杂一些的job
 */
public class MajorJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String jobName = dataMap.getString("jobName");
//        PersonMapper personMapper = (PersonMapper) dataMap.get("personMapper");
        PersonMapper personMapper = (PersonMapper) SpringContextJobUtil.getBean("personMapper");
        List<Person> personList = personMapper.queryList();

        System.out.println(Thread.currentThread().getName() + "--"
                + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()) + "--"
                + jobName + "--" + personList);
    }
}
