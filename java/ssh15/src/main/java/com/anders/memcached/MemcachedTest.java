package com.anders.memcached;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.spy.memcached.ConnectionFactory;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.ConnectionFactoryBuilder.Locator;
import net.spy.memcached.ConnectionFactoryBuilder.Protocol;
import net.spy.memcached.DefaultHashAlgorithm;
import net.spy.memcached.FailureMode;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.transcoders.WhalinV1Transcoder;

public class MemcachedTest {
	public void test1() {
		// <bean id="memcachedClient"
		// class="net.spy.memcached.spring.MemcachedClientFactoryBean">
		// <property name="servers"
		// value="host1:11211,host2:11211,host3:11211"/>
		// <property name="protocol" value="BINARY"/>
		// <property name="transcoder">
		// <bean class="net.spy.memcached.transcoders.SerializingTranscoder">
		// <property name="compressionThreshold" value="1024"/>
		// </bean>
		// </property>
		// <property name="opTimeout" value="1000"/>
		// <property name="timeoutExceptionThreshold" value="1998"/>
		// <property name="hashAlg" value="KETAMA_HASH"/>
		// <property name="locatorType" value="CONSISTENT"/>
		// <property name="failureMode" value="Redistribute"/>
		// <property name="useNagleAlgorithm" value="false"/>
		// </bean>
		try {
			MemcachedClient client = new MemcachedClient(new ArrayList());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test2() {
		InetSocketAddress node1 = new InetSocketAddress("127.0.0.1", 11212);
		InetSocketAddress node2 = new InetSocketAddress("127.0.0.1", 11213);
		InetSocketAddress node3 = new InetSocketAddress("127.0.0.1", 11214);
		InetSocketAddress node4 = new InetSocketAddress("127.0.0.1", 11215);
		InetSocketAddress node5 = new InetSocketAddress("127.0.0.1", 11216);
		InetSocketAddress node6 = new InetSocketAddress("127.0.0.1", 11217);
		List<InetSocketAddress> addrs = new ArrayList<InetSocketAddress>();
		addrs.add(node1);
		addrs.add(node2);
		addrs.add(node3);
		addrs.add(node4);
		addrs.add(node5);
		addrs.add(node6);
		// ConnectionFactory cf = new KetamaConnectionFactory();
		// ConnectionFactory cf = new
		// KetamaConnectionFactory(DefaultConnectionFactory.DEFAULT_OP_QUEUE_LEN,
		// DefaultConnectionFactory.DEFAULT_READ_BUFFER_SIZE, 1L);
		ConnectionFactoryBuilder builder = new ConnectionFactoryBuilder();
		builder.setTimeoutExceptionThreshold(1998);
		// builder.setMaxReconnectDelay(1);
		builder.setProtocol(Protocol.BINARY);
		builder.setFailureMode(FailureMode.Redistribute);
		builder.setTranscoder(new WhalinV1Transcoder());
		builder.setHashAlg(DefaultHashAlgorithm.KETAMA_HASH);
		builder.setLocatorType(Locator.CONSISTENT);
		builder.setUseNagleAlgorithm(false);
		ConnectionFactory cf = builder.build();
		MemcachedClient mc = null;
		try {
			mc = new MemcachedClient(cf, addrs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mc.set("1", 1000, "eric.sun");
		mc.set("name", 1000, "eric.sun");
		mc.set("sunxien", 1000, "eric.sun");
		mc.set("zhuzhen", 1000, "eric.sun");
		mc.set("wangzhen", 1000, "eric.sun");
		mc.set("lusong", 1000, "eric.sun");
		mc.set("songlaoshi", 1000, "eric.sun");
		mc.set("yaolaoban", 1000, "eric.sun");
		mc.set("fage", 1000, "eric.sun");
	}

	public void test3() throws Exception {
		Map<InetSocketAddress, InetSocketAddress> addressMap = new HashMap<InetSocketAddress, InetSocketAddress>();
		InetSocketAddress node1 = new InetSocketAddress("127.0.0.1", 11212);
		InetSocketAddress node2 = new InetSocketAddress("127.0.0.1", 11213);
		InetSocketAddress node3 = new InetSocketAddress("127.0.0.1", 11214);
		addressMap.put(node1, null);
		addressMap.put(node2, null);
		addressMap.put(node3, null);
		XMemcachedClientBuilder b = new XMemcachedClientBuilder(addressMap, new int[] { 1, 1, 1 });
		net.rubyeye.xmemcached.MemcachedClient mc = b.build();
		// sleep(10000);
		mc.set("1", 1000, "eric.sun");
		System.out.println("=====================finish 1");
		// sleep(10000);
		mc.set("name", 1000, "eric.sun");
		System.out.println("=====================finish 2");
		// sleep(10000);
		mc.set("sunxien", 1000, "eric.sun");
		System.out.println("=====================finish 3");
		mc.shutdown();

	}
}
