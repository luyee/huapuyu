package com.anders.ethan.dubbo;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.registry.zookeeper.ZookeeperRegistry;
import com.alibaba.dubbo.remoting.zookeeper.ZookeeperTransporter;

public class GreenRegistry extends ZookeeperRegistry {

	public GreenRegistry(URL url, ZookeeperTransporter zookeeperTransporter) {
		super(url, zookeeperTransporter);
	}

	private URL addGroup(URL url) {
		String dockerHost = url.getParameter("dockerhost");
		String side = url.getParameter(Constants.SIDE_KEY);
		if (StringUtils.isNotEmpty(dockerHost) && Constants.PROVIDER_SIDE.equals(side)) {
			url = url.setHost(dockerHost);
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
