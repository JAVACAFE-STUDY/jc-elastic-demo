package io.javacafe.demo.config;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.javacafe.demo.quartz.job.HelloJob;

@Configuration
public class QuartzConfig {

	@Bean
	public Scheduler schedulter() throws SchedulerException {
	    
		// JobDetail 생성
		JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
				.build();
		
		// Trigger 생성
		Trigger trigger = TriggerBuilder.newTrigger()
				.withSchedule(CronScheduleBuilder.cronSchedule("0/1 * * * * ?"))
				.build();
		
		// 스캐줄러 실행
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		//scheduler.start();
		//scheduler.scheduleJob(jobDetail, trigger);
		
		return scheduler;
	}

	
}
