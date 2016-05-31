package com.pgrabarczyk.spring.batch;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@PropertySource("classpath:config.properties")
@ComponentScan(basePackages = { "com.pgrabarczyk.spring.batch.*" })
@ImportResource({ "classpath:spring/batch/jobs/csvToDBJob.xml", "classpath:spring/batch/jobs/context.xml",
		"classpath:spring/batch/config/mysql/database.xml"/* , "classpath:spring/batch/config/oracle/database.xml" */ })
@EnableScheduling
public class App {

	public static void main(String[] args) throws InterruptedException {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(App.class);
		context.registerShutdownHook();
	}

}