package com.interest.quartz.task;

import com.interest.service.TaskService;
import com.interest.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Author xyl
 * @Create 2018-11-08 15:56
 * @Desc 写点注释吧
 **/
@Slf4j
public class SynAssetInfoTask implements Job {

    public static final String SERVICE_NAME = "taskServiceImpl";

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        getTaskService().pushOrderInfo();
        log.info("******推送订单定时任务执行完毕...");
    }

    public TaskService getTaskService() {
        return (TaskService) SpringUtil.getBean(SERVICE_NAME);
    }

}
