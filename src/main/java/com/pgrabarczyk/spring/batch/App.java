package com.pgrabarczyk.spring.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

	public static void main(String[] args) {
		csvToDB("mysql");
		csvToDB("oracle");
	}
	
	private static void csvToDB(String db) {
		String[] springConfig = {
				"spring/batch/config/"+db+"/database.xml",
				"spring/batch/config/"+db+"/context.xml",
				"spring/batch/jobs/csvToDBJob.xml"
				};

		ApplicationContext context = new ClassPathXmlApplicationContext(springConfig);

		JobLauncher jobLauncher = context.getBean("jobLauncher", JobLauncher.class);
		Job csvToMySQLJob = context.getBean("csvToDBJob", Job.class);

		try {

			JobExecution execution = jobLauncher.run(csvToMySQLJob, new JobParameters());
			log.debug(db + " Exit Status : " + execution.getStatus());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}