package com.anders.ethan.job.lts.domain;

import java.util.HashMap;
import java.util.Map;

import com.anders.ethan.job.lts.client.DataProcess;
import com.anders.ethan.job.lts.common.JobType;
import com.lts.core.commons.utils.StringUtils;
import com.lts.core.domain.Job;

public class JobEx extends Job {

	private static final long serialVersionUID = 4784942965297055989L;

	private JobType jobType = JobType.SIMPLE;
	// TODO Anders 后期自动计算分片数
	private int shardingNum = 1;
	private DataProcess dataProcess;

	public JobEx() {
		setTaskId(StringUtils.generateUUID());
	}

	public JobType getJobType() {
		return jobType;
	}

	public void setJobType(JobType jobType) {
		if (jobType != null) {
			this.jobType = jobType;
		}
	}

	public int getShardingNum() {
		return shardingNum;
	}

	public void setShardingNum(int shardingNum) {
		if (shardingNum > 1) {
			this.shardingNum = shardingNum;
		}
	}

	public Job clone() {
		Job job = new Job();

		// TODO Anders 后期更换为基础组建中Bean拷贝
		job.setCronExpression(getCronExpression());
		job.setNeedFeedback(isNeedFeedback());
		job.setPriority(getPriority());
		job.setReplaceOnExist(isReplaceOnExist());
		job.setRetryTimes(getRetryTimes());
		job.setSubmitNodeGroup(getSubmitNodeGroup());
		job.setTaskTrackerNodeGroup(getTaskTrackerNodeGroup());
		job.setTriggerTime(getTriggerTime());
		job.setTaskId(getTaskId());

		Map<String, String> newExtParams = new HashMap<String, String>();
		Map<String, String> originExtParams = getExtParams();
		if (originExtParams != null && !originExtParams.isEmpty()) {
			for (String key : originExtParams.keySet()) {
				newExtParams.put(key, originExtParams.get(key));
			}
		}

		job.setExtParams(newExtParams);

		return job;
	}

	public void setDataProcess(DataProcess dataProcess) {
		this.dataProcess = dataProcess;
	}
}
