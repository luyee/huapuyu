package com.anders.ethan.dubbo;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.registry.zookeeper.ZookeeperRegistry;
import com.alibaba.dubbo.remoting.zookeeper.ZookeeperTransporter;

public class GreenRegistry extends ZookeeperRegistry {

	public GreenRegistry(URL url, ZookeeperTransporter zookeeperTransporter) {
		super(url, zookeeperTransporter);
	}

	private URL addGroup(URL url) {
		System.out.println(url.getHost());
		String side = url.getParameter(Constants.SIDE_KEY);
		if (Constants.PROVIDER_SIDE.equals(side) || Constants.CONSUMER_SIDE.equals(side)) {
			url = url.setHost("192.168.56.101");
		}
		return url;
	}

	@Override
	protected void doRegister(URL url) {
		super.doRegister(addGroup(url));
	}

	@Override
	protected void doUnregister(URL url) {
		super.doUnregister(addGroup(url));
	}
}
