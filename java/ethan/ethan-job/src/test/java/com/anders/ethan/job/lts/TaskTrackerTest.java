package com.anders.ethan.job.lts;

import com.lts.tasktracker.TaskTracker;

public class TaskTrackerTest {
	public static void main(String[] args) {
		TaskTracker taskTracker = new TaskTracker();
		taskTracker.setJobRunnerClass(MyJobRunner.class);
		taskTracker.setRegistryAddress("zookeeper://192.168.56.101:2181");
		taskTracker.setNodeGroup("test_task_tracker");
		taskTracker.setWorkThreads(20);
		taskTracker.start();

		System.out.println("");
	}
}
