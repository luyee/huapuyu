package com.anders.ethan.job.lts.domain;

import java.util.HashMap;
import java.util.Map;

import com.anders.ethan.job.lts.common.JobType;
import com.anders.ethan.job.lts.process.DataProcess;
import com.lts.core.commons.utils.Assert;
import com.lts.core.commons.utils.StringUtils;
import com.lts.core.domain.Job;

public class JobEx extends Job {

	private static final long serialVersionUID = 4784942965297055989L;

	private JobType jobType = JobType.SIMPLE;
	// TODO Anders 后期自动计算分片数
	private int shardingCount = 1;
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

	public int getShardingCount() {
		return shardingCount;
	}

	public void setShardingCount(int shardingCount) {
		if (shardingCount > 1) {
			this.shardingCount = shardingCount;
		}
	}

	public DataProcess getDataProcess() {
		return dataProcess;
	}

	public void setDataProcess(DataProcess dataProcess) {
		Assert.notNull(dataProcess, "dataProcess is null");

		this.dataProcess = dataProcess;
		this.dataProcess.setShardingCount(shardingCount);
		this.dataProcess.setOriginalParams(getExtParams());
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

		if (jobType.equals(JobType.SIMPLE) || dataProcess == null) {
			Map<String, String> newExtParams = new HashMap<String, String>();
			Map<String, String> originExtParams = getExtParams();
			if (originExtParams != null && !originExtParams.isEmpty()) {
				for (String key : originExtParams.keySet()) {
					newExtParams.put(key, originExtParams.get(key));
				}
			}

			job.setExtParams(newExtParams);
		}

		return job;
	}
}
