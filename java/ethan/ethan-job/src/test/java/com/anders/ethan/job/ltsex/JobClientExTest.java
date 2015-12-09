package com.anders.ethan.job.ltsex;

import com.anders.ethan.job.lts.client.JobClientEx;
import com.anders.ethan.job.lts.domain.JobEx;
import com.lts.jobclient.domain.Response;

public class JobClientExTest {
	public static void main(String[] args) {
		JobClientEx jobClientEx = new JobClientEx();
		jobClientEx.setNodeGroup("test_job_client_ex");
		jobClientEx.setClusterName("test_cluster");
		jobClientEx.setRegistryAddress("zookeeper://192.168.56.101:2181");
		jobClientEx.start();

		JobEx jobEx = new JobEx();
		jobEx.setTaskId("asdfasdf");
		jobEx.setParam("orderId", "1,2,3,4,5,6,7,8,98,0,45,45,5,6,7,8");
		jobEx.setTaskTrackerNodeGroup("test_task_tracker");
		jobEx.setNeedFeedback(false);
		jobEx.setReplaceOnExist(true);
		jobEx.setShardingCount(5);
		// jobEx.setJobType(JobType.SHARDING);
		// jobEx.setDataProcess(new SplitProcessImpl());
		jobEx.setCronExpression("*/1 * * * * ?");
		// job.setTriggerTime(DateUtils.addDay(new Date(), 1));
		Response response = jobClientEx.submitJobEx(jobEx);

		System.out.println(response);

		jobClientEx.stop();
	}
}
