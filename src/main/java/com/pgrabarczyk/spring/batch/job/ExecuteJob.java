package com.pgrabarczyk.spring.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ExecuteJob {

	private static final String JOB_NAME = "csvToDBJob";

	@Autowired
	private JobLocator jobLocator;

	@Autowired
	private JobLauncher jobLauncher;

	public void executeInternal() {

		try {
			Job csvToDBJob = jobLocator.getJob(JOB_NAME);
			JobExecution execution = jobLauncher.run(csvToDBJob, new JobParameters());
			log.debug("Job finished. Status : {}", execution.getStatus());

		} catch (JobExecutionException e) {
			e.printStackTrace();
		}
	}
}
