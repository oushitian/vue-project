package com.interest.listen;

import com.interest.quartz.WSScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @Author xyl
 * @Create 2018-11-08 15:48
 * @Desc 注册SpringBoot启动完成事件监听，用于启动job任务
 **/
@SpringBootConfiguration
public class SchedulerListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    public WSScheduler wsScheduler;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        wsScheduler.setScheduler(schedulerFactoryBean.getScheduler());
        //如果不用动态设置，这里可以直接开启
        wsScheduler.scheduleJobs();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        return schedulerFactoryBean;
    }

}
