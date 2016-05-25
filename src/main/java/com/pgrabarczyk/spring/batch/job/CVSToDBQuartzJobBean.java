package com.pgrabarczyk.spring.batch.job;

import org.quartz.JobExecutionContext;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.quartz.QuartzJobBean;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CVSToDBQuartzJobBean extends QuartzJobBean {

	@Setter
	private String jobName;

	@Setter
	private JobLocator jobLocator;

	@Setter
	private JobLauncher jobLauncher;

	protected void executeInternal(JobExecutionContext context) {

		try {
			Job csvToDBJob = jobLocator.getJob(jobName);
			JobExecution execution = jobLauncher.run(csvToDBJob, new JobParameters());
			log.debug("Job finished. Status : {} {}", execution.getStatus(), execution.getId());
			
		} catch (JobExecutionException e) {
			e.printStackTrace();
		}
	}
}
