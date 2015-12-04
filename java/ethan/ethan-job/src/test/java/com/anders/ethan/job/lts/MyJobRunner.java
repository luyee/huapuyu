package com.anders.ethan.job.lts;

import com.lts.core.domain.Action;
import com.lts.core.domain.Job;
import com.lts.tasktracker.Result;
import com.lts.tasktracker.logger.BizLogger;
import com.lts.tasktracker.runner.JobRunner;
import com.lts.tasktracker.runner.LtsLoggerFactory;

public class MyJobRunner implements JobRunner {
	 private final static BizLogger bizLogger = LtsLoggerFactory.getBizLogger();
	    @Override
	    public Result run(Job job) throws Throwable {
	        try {
	         System.out.println(job);
	            bizLogger.info("测试，业务日志啊啊啊啊啊");

	        } catch (Exception e) {
	            return new Result(Action.EXECUTE_FAILED, e.getMessage());
	        }
	        return new Result(Action.EXECUTE_SUCCESS, "执行成功了，哈哈");
	    }
}
