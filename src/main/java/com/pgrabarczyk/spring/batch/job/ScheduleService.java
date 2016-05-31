package com.pgrabarczyk.spring.batch.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

	@Autowired
	private ExecuteJob executeJob;

	@Scheduled(cron = "0/20 * * * * ?") // every 20 sec
	public void run() {
		executeJob.executeInternal();
	}
}
