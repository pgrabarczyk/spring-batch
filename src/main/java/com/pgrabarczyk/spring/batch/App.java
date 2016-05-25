package com.pgrabarczyk.spring.batch;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

	public static void main(String[] args) {
		csvToDB("mysql");
		//csvToDB("oracle");
	}
	
	private static void csvToDB(String db) {
		String[] springConfig = {
				"spring/batch/jobs/csvToDBJob.xml",
				"spring/batch/jobs/job-quartz.xml",
				"spring/batch/config/"+db+"/database.xml"
				};

		ApplicationContext context = new ClassPathXmlApplicationContext(springConfig);
	}
}