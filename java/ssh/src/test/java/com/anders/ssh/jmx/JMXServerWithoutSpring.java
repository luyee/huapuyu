package com.anders.ssh.jmx;

import java.lang.management.ManagementFactory;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

public class JMXServerWithoutSpring {

	public static void main(String[] args) throws InterruptedException, RemoteException {
		Anders anders = new Anders();

		synchronized (anders) {
			try {
				MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
				ObjectName objectName = new ObjectName("myjmx:type=anders");
				if (!mbeanServer.isRegistered(objectName)) {
					String user = "admin";
					String pwd = "123";
					String[] credentials = new String[] { user, pwd };
					Map<String, String[]> props = new HashMap<String, String[]>();
					props.put("jmx.remote.credentials", credentials);

					LocateRegistry.createRegistry(1234);

					JMXServiceURL url = new JMXServiceURL("service:jmx:rmi://127.0.0.1/jndi/rmi://127.0.0.1:1234/jmxrmi");
					// 下面方式也可以
					// JMXServiceURL url = new JMXServiceURL("service:jmx:rmi://         /jndi/rmi://127.0.0.1:1234/jmxrmi");
					JMXConnectorServer connector = JMXConnectorServerFactory.newJMXConnectorServer(url, props, mbeanServer);
					connector.start();

					mbeanServer.registerMBean(anders, objectName);
				}
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		System.out.println("jmx server started...");

		Object lock = new Object();
		synchronized (lock) {
			lock.wait();
		}
	}

}
