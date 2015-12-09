package com.anders.ethan.job.lts.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anders.ethan.job.lts.domain.JobEx;
import com.anders.ethan.job.lts.process.DataProcess;
import com.lts.core.commons.utils.Assert;
import com.lts.core.commons.utils.CollectionUtils;
import com.lts.core.domain.Job;
import com.lts.core.json.JSON;
import com.lts.core.support.RetryScheduler;
import com.lts.jobclient.JobClient;
import com.lts.jobclient.RetryJobClient;
import com.lts.jobclient.domain.JobClientApplication;
import com.lts.jobclient.domain.JobClientNode;
import com.lts.jobclient.domain.Response;
import com.lts.jobclient.domain.ResponseCode;
import com.lts.jobclient.support.JobSubmitProtectException;

public class JobClientEx extends JobClient<JobClientNode, JobClientApplication> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(JobClientEx.class);

	private RetryScheduler<Job> retryScheduler;

	@Override
	protected void beforeStart() {
		super.beforeStart();
		retryScheduler = new RetryScheduler<Job>(application, 30) {
			@Override
			protected boolean isRemotingEnable() {
				return isServerEnable();
			}

			@Override
			protected boolean retry(List<Job> jobs) {
				try {
					// 重试必须走同步，不然会造成文件锁，死锁
					return superSubmitJob(jobs).isSuccess();
				} catch (Throwable t) {
					RetryScheduler.LOGGER.error(t.getMessage(), t);
				}
				return false;
			}
		};
		retryScheduler.setName(RetryJobClient.class.getSimpleName());
		retryScheduler.start();
	}

	@Override
	protected void beforeStop() {
		super.beforeStop();
		retryScheduler.stop();
	}

	@Override
	public Response submitJob(Job job) {
		return submitJob(Collections.singletonList(job));
	}

	@Override
	public Response submitJob(List<Job> jobs) {
		Response response;
		try {
			response = superSubmitJob(jobs);
		} catch (JobSubmitProtectException e) {
			response = new Response();
			response.setSuccess(true);
			response.setFailedJobs(jobs);
			response.setCode(ResponseCode.SUBMIT_TOO_BUSY_AND_SAVE_FOR_LATER);
			response.setMsg(response.getMsg()
					+ ", submit too busy , save local fail store and send later !");
			LOGGER.warn(JSON.toJSONString(response));
			return response;
		}
		if (!response.isSuccess()) {
			try {
				for (Job job : response.getFailedJobs()) {
					retryScheduler.inSchedule(job.getTaskId(), job);
				}
				response.setSuccess(true);
				response.setCode(ResponseCode.SUBMIT_FAILED_AND_SAVE_FOR_LATER);
				response.setMsg(response.getMsg()
						+ ", save local fail store and send later !");
				LOGGER.warn(JSON.toJSONString(response));
			} catch (Exception e) {
				response.setSuccess(false);
				response.setMsg(e.getMessage());
			}
		}

		return response;
	}

	private Response superSubmitJob(List<Job> jobs) {
		return super.submitJob(jobs);
	}

	// TODO Anders 后期修改源码开启同步
	// private Response superSubmitJob(List<Job> jobs, SubmitType type) {
	// return super.submitJob(jobs, type);
	// }

	public Response submitJobEx(JobEx jobEx) {
		Assert.notNull(jobEx, "job is null");

		switch (jobEx.getJobType()) {
		case SHARDING:
			return submitJob(splitJob(jobEx));
		default:
			return submitJob(jobEx.clone());
		}
	}

	private List<Job> splitJob(JobEx jobEx) {
		DataProcess dataProcess = jobEx.getDataProcess();
		if (dataProcess != null) {
			dataProcess.process();
		}

		List<Job> jobs = new ArrayList<>();
		for (int i = 0; i < jobEx.getShardingCount(); i++) {
			Job job = jobEx.clone();
			job.setTaskId(job.getTaskId() + "_" + i);
			if (dataProcess != null) {
				job.setExtParams(jobEx.getDataProcess().getSliceParams(i));
			}

			jobs.add(job);
		}

		if (CollectionUtils.isEmpty(jobs)) {
			throw new RuntimeException("error occurs split job");
		}

		return jobs;
	}

}
