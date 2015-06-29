package com.anders.es;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.junit.Test;

public class ClientTest {

	@Test
	public void test1() {
		Client client = new TransportClient().addTransportAddress(new InetSocketTransportAddress("lu1", 9300))
				.addTransportAddress(new InetSocketTransportAddress("lu2", 9300))
				.addTransportAddress(new InetSocketTransportAddress("lu3", 9300));
		client.close();
	}

	@Test
	public void test2() {
		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "anders").build();
		Client client = new TransportClient(settings);
		client.close();
	}
}
