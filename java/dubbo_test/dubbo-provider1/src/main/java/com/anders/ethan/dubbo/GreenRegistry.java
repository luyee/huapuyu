package com.anders.ethan.dubbo;

import java.net.InetAddress;
import java.net.UnknownHostException;

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
		if (StringUtils.isNotEmpty(dockerHost)
				&& Constants.PROVIDER_SIDE.equals(side)) {
			url = url.setHost(dockerHost);
		}
		return url;
	}

	public String getIP(String name) {
		InetAddress address = null;
		try {
			address = InetAddress.getByName(name);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}
		return address.getHostAddress().toString();
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
