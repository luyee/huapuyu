package com.anders.ssh.jmx;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class JMXClientWithoutSpring {

	public static void main(String[] args) throws InterruptedException, IOException, MalformedObjectNameException {
		String user = "admin";
		String pwd = "123";
		String[] credentials = new String[] { user, pwd };
		Map<String, String[]> props = new HashMap<String, String[]>();
		props.put("jmx.remote.credentials", credentials);

		JMXServiceURL url = new JMXServiceURL("service:jmx:rmi://127.0.0.1/jndi/rmi://127.0.0.1:1234/jmxrmi");
		JMXConnector connector = JMXConnectorFactory.connect(url, props);
		MBeanServerConnection msc = connector.getMBeanServerConnection();
		connector.connect();

		ObjectName objectName = new ObjectName("myjmx:type=anders");
		if (msc.isRegistered(objectName)) {
			for (Object object : msc.queryMBeans(null, null)) {
				System.out.println(object);
			}
		}

		AndersMBean anders = JMX.newMBeanProxy(msc, objectName, AndersMBean.class);
		anders.setId(1L);
		System.out.println(anders.getId());

	}

}
