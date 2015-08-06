package com.anders.es;

import java.io.IOException;
import java.util.Date;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.junit.Test;

public class ClientTest {

	@Test
	public void test1() throws IOException {
		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "anders")
				.put("index.refresh_interval", "1s").build();
		TransportClient client = new TransportClient(settings);
		client.addTransportAddress(new InetSocketTransportAddress("lu1", 9300))
				.addTransportAddress(new InetSocketTransportAddress("lu2", 9300))
				.addTransportAddress(new InetSocketTransportAddress("lu3", 9300));

		// Node node = nodeBuilder().client(true).node();
		// Client client1 = node.client();

		XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("name", "tom")
				.field("time", new Date()).field("age", 33).endObject();

		// client.prepareIndex("hint", "user").setSource(builder.string()).setRefresh(true);
		IndexResponse indexResponse = client.prepareIndex("hint", "user").setSource(builder.string()).execute()
				.actionGet();

		System.out.println(indexResponse.getId());
		System.out.println(indexResponse.getVersion());
		System.out.println(indexResponse.getIndex());
		System.out.println(indexResponse.getType());

		GetResponse getResponse = client.prepareGet("hint", "user", indexResponse.getId()).execute().actionGet();
		System.out.println(getResponse.getSource());

		SearchResponse searchResponse = client.prepareSearch("hint").setTypes("user")
				.setSearchType(SearchType.DFS_QUERY_AND_FETCH).setQuery(QueryBuilders.termQuery("name", "tom"))
				// .setPostFilter(FilterBuilders.rangeFilter("size").from(1).to(10))
				.setFrom(0).setSize(10).setExplain(true).execute().actionGet();

		for (SearchHit searchHit : searchResponse.getHits()) {
			System.out.println(searchHit.getSourceAsString());
		}

		client.close();
	}
}
