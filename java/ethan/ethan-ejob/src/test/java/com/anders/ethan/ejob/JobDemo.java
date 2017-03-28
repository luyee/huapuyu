package com.anders.ethan.ejob;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

public class JobDemo {
	   public static void main(String[] args) {
		   JobCoreConfiguration coreConfig = JobCoreConfiguration.newBuilder("javaSimpleJob", "0 0/1 * * * ?", 3).shardingItemParameters("0=Beijing,1=Shanghai,2=Guangzhou").build();
	        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(coreConfig, JavaSimpleJob.class.getCanonicalName());
//	        new JobScheduler(createRegistryCenter(), LiteJobConfiguration.newBuilder(simpleJobConfig).build(), jobEventConfig, new JavaSimpleListener(), new JavaSimpleDistributeListener(1000L, 2000L)).init();
	        new JobScheduler(createRegistryCenter(), LiteJobConfiguration.newBuilder(simpleJobConfig).build()).init();
		   
//	        new JobScheduler(createRegistryCenter(), createJobConfiguration()).init();
	    }
	    
	    private static CoordinatorRegistryCenter createRegistryCenter() {
	        CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration("192.168.56.121:2181", "elastic-job-demo"));
	        regCenter.init();
	        return regCenter;
	    }
	    
}
