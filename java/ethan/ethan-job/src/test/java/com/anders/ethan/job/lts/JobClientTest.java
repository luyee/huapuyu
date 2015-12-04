package com.anders.ethan.job.lts;

import com.lts.core.commons.utils.StringUtils;
import com.lts.core.domain.Job;
import com.lts.jobclient.JobClient;
import com.lts.jobclient.RetryJobClient;
import com.lts.jobclient.domain.Response;

public class JobClientTest {
	public static void main(String[] args) {
		  // 推荐使用RetryJobClient
        JobClient jobClient = new RetryJobClient();
        jobClient.setNodeGroup("test_jobClient");
        jobClient.setClusterName("test_cluster");
        jobClient.setRegistryAddress("zookeeper://192.168.56.101:2181");
//        jobClient.setJobFinishedHandler(new JobCompletedHandlerImpl());
        // master 节点变化监听器，当有集群中只需要一个节点执行某个事情的时候，可以监听这个事件
//        jobClient.addMasterChangeListener(new MasterChangeListenerImpl());
        jobClient.start();

        Job job = new Job();
        job.setTaskId(StringUtils.generateUUID());
        job.setParam("shopId", "1122222221");
        job.setTaskTrackerNodeGroup("test_trade_TaskTracker");
        job.setNeedFeedback(false);
        job.setReplaceOnExist(true);        // 当任务队列中存在这个任务的时候，是否替换更新
//        job.setCronExpression("0 0/1 * * * ?");
//        job.setTriggerTime(DateUtils.addDay(new Date(), 1));
        Response response = jobClient.submitJob(job);
        System.out.println(response);
        
        jobClient.stop();
	}
}
