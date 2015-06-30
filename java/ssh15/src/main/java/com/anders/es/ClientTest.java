package com.anders.es;

import java.io.IOException;
import java.util.Date;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Test;

public class ClientTest {

	@Test
	public void test1() throws IOException {
		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "anders").build();
		TransportClient client = new TransportClient(settings);
		client.addTransportAddress(new InetSocketTransportAddress("lu1", 9300))
				.addTransportAddress(new InetSocketTransportAddress("lu2", 9300))
				.addTransportAddress(new InetSocketTransportAddress("lu3", 9300));

		XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("name", "tom")
				.field("time", new Date()).field("age", 33).endObject();

		IndexResponse indexResponse = client.prepareIndex("hint", "user").setSource(builder.string()).execute()
				.actionGet();

		System.out.println(indexResponse.getId());
		System.out.println(indexResponse.getVersion());
		System.out.println(indexResponse.getIndex());
		System.out.println(indexResponse.getType());

		GetResponse getResponse = client.prepareGet("hint", "user", indexResponse.getId()).execute().actionGet();
		System.out.println(getResponse.getSource());

		client.close();
	}
}
