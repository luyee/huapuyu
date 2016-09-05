package com.anders.ethan.job.quartz;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleRecoveryJob implements Job {

	private static Logger _log = LoggerFactory
			.getLogger(SimpleRecoveryJob.class);

	private static final String COUNT = "count";

	public SimpleRecoveryJob() {
	}

	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		JobKey jobKey = context.getJobDetail().getKey();

		// if the job is recovering print a message
		if (context.isRecovering()) {
			_log.info("SimpleRecoveryJob: " + jobKey + " RECOVERING at "
					+ new Date());
		} else {
			_log.info("SimpleRecoveryJob: " + jobKey + " starting at "
					+ new Date());
		}

		// delay for ten seconds
		long delay = 10L * 1000L;
		try {
			Thread.sleep(delay);
		} catch (Exception e) {
		}

		JobDataMap data = context.getJobDetail().getJobDataMap();
		int count;
		if (data.containsKey(COUNT)) {
			count = data.getInt(COUNT);
		} else {
			count = 0;
		}
		count++;
		data.put(COUNT, count);

		_log.info("SimpleRecoveryJob: " + jobKey + " done at " + new Date()
				+ "\n Execution #" + count);

	}

}