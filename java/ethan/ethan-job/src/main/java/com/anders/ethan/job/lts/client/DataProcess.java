package com.anders.ethan.job.lts.client;

import com.anders.ethan.job.lts.domain.JobEx;

public abstract class DataProcess {
	private JobEx jobEx;

	public JobEx getJobEx() {
		return jobEx;
	}

	public void setJobEx(JobEx jobEx) {
		this.jobEx = jobEx;
	}

	abstract void process();
}
