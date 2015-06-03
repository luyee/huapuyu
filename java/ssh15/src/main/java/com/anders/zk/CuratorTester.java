package com.anders.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

public class CuratorTester {
	public static void main(String[] args) {
//		String path = "/test_path";  
	     CuratorFramework client = CuratorFrameworkFactory.builder().
	    		 connectString("e4430:2181").
	    		 //namespace("/").
	    		 retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000)).
	    		 connectionTimeoutMs(5000).
	    		 build();  
	     client.start();  
	     
	     System.out.println(client.getState());
	}
}
