package com.anders.ethan.ejob;

import java.util.Date;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

public class JavaSimpleJob implements SimpleJob {

	@Override
	public void execute(final ShardingContext shardingContext) {
		System.out.println(String.format("------Thread ID: %s, Date: %s, Sharding Context: %s, Action: %s",
				Thread.currentThread().getId(), new Date(), shardingContext, "simple job"));
	}
}
