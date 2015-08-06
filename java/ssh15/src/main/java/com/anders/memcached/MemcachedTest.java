package com.anders.memcached;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import net.spy.memcached.MemcachedClient;

public class MemcachedTest {
	@Test
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
}
