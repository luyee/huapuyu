package com.anders.ethan.ejob;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;

public class JavaSimpleListener implements ElasticJobListener {
    
    @Override
    public void beforeJobExecuted(final ShardingContexts shardingContexts) {
        System.out.println("beforeJobExecuted:" + shardingContexts.getJobName());
    }
    
    @Override
    public void afterJobExecuted(final ShardingContexts shardingContexts) {
        System.out.println("afterJobExecuted:" + shardingContexts.getJobName());
    }
}
