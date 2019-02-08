package io.javacafe.demo.quartz.job;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloJob implements Job {

	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		log.info("Hello Job이 실행되었습니다. 실행시간은 {}입니다.", dateFormat.format(date));
	}

	
}


