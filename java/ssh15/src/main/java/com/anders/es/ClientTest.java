package com.anders.es;

import java.io.IOException;
import java.util.Date;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;

public class ClientTest {

	@Test
	public void test1() throws IOException {
		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "anders").put("index.refresh_interval", "1s").build();
		TransportClient client = new TransportClient(settings);
		client.addTransportAddress(new InetSocketTransportAddress("lu1", 9300)).addTransportAddress(new InetSocketTransportAddress("lu2", 9300))
				.addTransportAddress(new InetSocketTransportAddress("lu3", 9300));

		// Node node = nodeBuilder().client(true).node();
		// Client client1 = node.client();

		XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("name", "tom").field("time", new Date()).field("age", 33).endObject();

		// client.prepareIndex("hint",
		// "user").setSource(builder.string()).setRefresh(true);
		IndexResponse indexResponse = client.prepareIndex("hint", "user").setSource(builder.string()).execute().actionGet();

		System.out.println(indexResponse.getId());
		System.out.println(indexResponse.getVersion());
		System.out.println(indexResponse.getIndex());
		System.out.println(indexResponse.getType());

		GetResponse getResponse = client.prepareGet("hint", "user", indexResponse.getId()).execute().actionGet();
		System.out.println(getResponse.getSource());

		SearchResponse searchResponse = client.prepareSearch("hint").setTypes("user").setSearchType(SearchType.DFS_QUERY_AND_FETCH)
				.setQuery(QueryBuilders.termQuery("name", "tom"))
				// .setPostFilter(FilterBuilders.rangeFilter("size").from(1).to(10))
				.setFrom(0).setSize(10).setExplain(true).execute().actionGet();

		for (SearchHit searchHit : searchResponse.getHits()) {
			System.out.println(searchHit.getSourceAsString());
		}

		client.close();
	}

	@Test
	public void test2() throws IOException {
		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "dev64").put("index.refresh_interval", "60s").build();
		TransportClient client = new TransportClient(settings);
		client.addTransportAddress(new InetSocketTransportAddress("127.0.0.1", 9300));

		// Node node = nodeBuilder().client(true).node();
		// Client client1 = node.client();

		XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("name", "tom").field("time", new Date()).field("age", 33).endObject();

		// client.prepareIndex("hint",
		// "user").setSource(builder.string()).setRefresh(true);
		IndexResponse indexResponse = client.prepareIndex("hint", "user").setSource(builder.string()).execute().actionGet();

		System.out.println(indexResponse.getId());
		System.out.println(indexResponse.getVersion());
		System.out.println(indexResponse.getIndex());
		System.out.println(indexResponse.getType());

		GetResponse getResponse = client.prepareGet("hint", "user", indexResponse.getId()).execute().actionGet();
		System.out.println(getResponse.getSource());

		SearchResponse searchResponse = client.prepareSearch("hint").setTypes("user").setSearchType(SearchType.DFS_QUERY_AND_FETCH)
				.setQuery(QueryBuilders.termQuery("name", "tom"))
				// .setPostFilter(FilterBuilders.rangeFilter("size").from(1).to(10))
				.setFrom(0).setSize(10).setExplain(true).execute().actionGet();

		for (SearchHit searchHit : searchResponse.getHits()) {
			System.out.println(searchHit.getSourceAsString());
		}

		client.close();
	}

	// 以节点方式运行，创建数据
	@Test
	public void test3() throws IOException {
		Settings settings = ImmutableSettings.settingsBuilder()
		// .put("client.transport.sniff", true)
				.put("cluster.name", "dev64")
				// .put("index.translog.flush_threshold_ops", "100000")
				// index.translog.flush_threshold_size
				// index.translog.flush_threshold_period
				.put("node.name", "ethan").put("index.refresh_interval", "-1").build();

		Node node = NodeBuilder.nodeBuilder().settings(settings).client(true).node();

		Client client = node.client();

		XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("name", "zhuzhen").field("time", new Date()).field("age", 33).endObject();

		// IndexResponse indexResponse = client.prepareIndex("hint", "user")
		// .setSource(builder.string()).setRefresh(true).execute()
		// .actionGet();

		BulkRequestBuilder bulkRequest = client.prepareBulk();

		for (int i = 0; i < 20; i++)
			bulkRequest.add(client.prepareIndex("hint", "user").setSource(builder.string()).setId(String.valueOf(i)));

		BulkResponse bulkResponse = bulkRequest.setRefresh(false).execute().actionGet();
		if (bulkResponse.hasFailures()) {
			System.out.println(bulkResponse.buildFailureMessage());
		}

		// System.out.println(indexResponse.getId());
		// System.out.println(indexResponse.getVersion());
		// System.out.println(indexResponse.getIndex());
		// System.out.println(indexResponse.getType());

		// System.out.println("**************first query***********");

		// SearchResponse searchResponse = client.prepareSearch("hint")
		// .setTypes("user").setSearchType(SearchType.DFS_QUERY_AND_FETCH)
		// .setQuery(QueryBuilders.termQuery("name", "zhuzhen"))
		// // .setPostFilter(FilterBuilders.rangeFilter("size").from(1).to(10))
		// .setFrom(0).setSize(10).setExplain(true).execute().actionGet();
		//
		// for (SearchHit searchHit : searchResponse.getHits()) {
		// System.out.println("result:" + searchHit.getSourceAsString());
		// }

		// System.out.println("**************son***********");
		//
		// Client client1 = node.client();
		//
		// SearchResponse searchResponse1 = client1.prepareSearch("hint")
		// .setTypes("user").setSearchType(SearchType.DFS_QUERY_AND_FETCH)
		// .setQuery(QueryBuilders.termQuery("name", "zhuzhen"))
		// // .setPostFilter(FilterBuilders.rangeFilter("size").from(1).to(10))
		// .setFrom(0).setSize(10).setExplain(true).execute().actionGet();
		//
		// for (SearchHit searchHit : searchResponse1.getHits()) {
		// System.out.println("result:" + searchHit.getSourceAsString());
		// }
		//
		// client1.close();
		//
		// System.out.println("**************son***********");

		// System.out.println("**************second query***********");

		// client.admin().indices().refresh(new RefreshRequest("hint"))
		// .actionGet();

		// searchResponse = client.prepareSearch("hint").setTypes("user")
		// .setSearchType(SearchType.DFS_QUERY_AND_FETCH)
		// .setQuery(QueryBuilders.termQuery("name", "zhuzhen"))
		// // .setPostFilter(FilterBuilders.rangeFilter("size").from(1).to(10))
		// .setFrom(0).setSize(10).setExplain(true).execute().actionGet();
		//
		// for (SearchHit searchHit : searchResponse.getHits()) {
		// System.out.println("result:" + searchHit.getSourceAsString());
		// }

		client.close();
	}

	// 以节点方式运行，查询数据
	@Test
	public void test4() throws IOException {
		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "dev64").put("index.refresh_interval", "-1").build();

		Node node = NodeBuilder.nodeBuilder().settings(settings).client(true).node();
		Client client = node.client();

		// GetResponse getResponse = client.prepareGet("hint", "user", "123")
		// .execute().actionGet();
		// System.out.println(getResponse.getSource());

		// client.admin().indices().refresh(new RefreshRequest("hint"));

		SearchResponse searchResponse = client.prepareSearch("hint").setTypes("user").setSearchType(SearchType.DFS_QUERY_AND_FETCH)
				.setQuery(QueryBuilders.termQuery("name", "zhuzhen"))
				// .setPostFilter(FilterBuilders.rangeFilter("size").from(1).to(10))
				.setFrom(0).setSize(10).setExplain(true).execute().actionGet();

		for (SearchHit searchHit : searchResponse.getHits()) {
			System.out.println("result:" + searchHit.getSourceAsString());
		}

		client.close();
	}

	@Test
	public void test5() throws IOException {
		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "dev64").put("index.refresh_interval", "-1").build();
		TransportClient client = new TransportClient(settings);
		client.addTransportAddress(new InetSocketTransportAddress("127.0.0.1", 9300)).addTransportAddress(new InetSocketTransportAddress("127.0.0.1", 9301))
				.addTransportAddress(new InetSocketTransportAddress("127.0.0.1", 9302));

		// client.admin().indices().refresh(new RefreshRequest("hint"));

		SearchResponse searchResponse = client.prepareSearch("hint").setTypes("user").setSearchType(SearchType.DFS_QUERY_AND_FETCH)
				.setQuery(QueryBuilders.termQuery("name", "zhuzhen"))
				// .setPostFilter(FilterBuilders.rangeFilter("size").from(1).to(10))
				.setFrom(0).setSize(10).setExplain(true).execute().actionGet();

		SortBuilders sortBuilders = new SortBuilders();
		SortBuilders.fieldSort("_id").order(SortOrder.ASC);

		for (SearchHit searchHit : searchResponse.getHits()) {
			System.out.println(searchHit.getId() + ":\t" + searchHit.getSourceAsString());
		}

		client.close();
	}
}
