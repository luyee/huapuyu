package com.anders.ethan.es.sql;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.ExistsQueryBuilder;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.ElasticSearchDruidDataSourceFactory;

public class SqlTest {

	public void test() throws Exception {
		Properties properties = new Properties();
		properties.put("url", "jdbc:elasticsearch://192.168.56.101:9300/ni-database-zhuzhen");
		DruidDataSource dds = (DruidDataSource) ElasticSearchDruidDataSourceFactory.createDataSource(properties);
		Connection connection = dds.getConnection();
		PreparedStatement ps = connection.prepareStatement("SELECT * from ni-database-zhuzhen where empname='emp11'");
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			System.out.println(resultSet.getString("empname"));
		}
		ps.close();
		connection.close();
		dds.close();
	}

	@Test
	public void test1() throws Exception {
		Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();

		TransportClient client = TransportClient.builder().settings(settings).build();

		try {
			String[] ipStrs = "172.16.1.33".split("\\.");
			byte[] ip = new byte[4];
			for (int i = 0; i < 4; i++) {
				ip[i] = (byte) (Integer.parseInt(ipStrs[i]) & 0xff);
			}

			client = client.addTransportAddress((new InetSocketTransportAddress(InetAddress.getByAddress(ip), 9300)));
		} catch (UnknownHostException e) {
			System.out.println(e.getMessage());
		}

		// BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();QueryBuilders.
		// boolQuery.filter(QueryBuilders.termQuery("traceId", traceId));
		//
		// QueryBuilders.filteredQuery(queryBuilder, filterBuilder)

		// QueryStringQueryBuilder queryBuilder = new
		// QueryStringQueryBuilder("中国经济");
		// queryBuilder.analyzer("ik").field("title");
		//
		// FilteredQueryBuilder query = QueryBuilders.
		//
		// FilteredQueryBuilder query = QueryBuilders.filteredQuery(new Query,
		// filterBuilder)

		for (int i = 0; i < 100000; i++) {
			// rangeQuery(client);
			// existsQuery(client);
			idsQuery(client);
		}
	}

	private void rangeQuery(TransportClient client) throws ParseException {
		String from = "2020-02-13 00:00:00";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date fromDate = simpleDateFormat.parse(from);

		RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("expripration_time").gte(fromDate.getTime());

		long begin = new Date().getTime();
		SearchResponse searchResponse = client.prepareSearch("eif_market").setTypes("t_market_coupon_user")
				.setQuery(rangeQueryBuilder).setSize(10000).execute().actionGet();
		System.out.println(
				"count : " + searchResponse.getHits().getHits().length + "; time : " + (new Date().getTime() - begin));

		// for (int i = 0; i < searchResponse.getHits().getHits().length; i++) {
		// System.out.println(searchResponse.getHits().getHits()[i]
		// .getSourceAsString());
		// }
	}

	private void existsQuery(TransportClient client) throws ParseException {
		ExistsQueryBuilder existsQueryBuilder = QueryBuilders.existsQuery("issue_dpt");

		long begin = new Date().getTime();
		SearchResponse searchResponse = client.prepareSearch("eif_market").setTypes("t_market_coupon_user")
				.setQuery(existsQueryBuilder).setExplain(true).setSize(10000).execute().actionGet();

		System.out.println(
				"count : " + searchResponse.getHits().getHits().length + "; time : " + (new Date().getTime() - begin));
	}

	private void idsQuery(TransportClient client) throws ParseException {
		IdsQueryBuilder idsQueryBuilder = QueryBuilders.idsQuery("t_market_coupon_user").addIds("96550620");

		long begin = new Date().getTime();
		SearchResponse searchResponse = client.prepareSearch("eif_market").setTypes("t_market_coupon_user")
				.setQuery(idsQueryBuilder).setSize(10000).execute().actionGet();

		System.out.println(
				"count : " + searchResponse.getHits().getHits().length + "; time : " + (new Date().getTime() - begin));
		System.out.println(searchResponse.getHits().getHits()[0].getSourceAsString());
	}

	@Test
	public void test2() throws Exception {
		Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();

		String[] hosts = {"192.168.56.101:9300","192.168.56.102:9300","192.168.56.103:9300","192.168.56.104:9300","192.168.56.105:9300","192.168.56.106:9300"};
		
		TransportClient transportClient = TransportClient.builder().settings(settings).build();
		for (String host : hosts) {
			String[] address = host.split(":");
			String[] ipStrs = address[0].split("\\.");
			byte[] ip = new byte[4];
			for (int i = 0; i < 4; i++) {
				ip[i] = (byte) (Integer.parseInt(ipStrs[i]) & 0xff);
			}
			int port = Integer.parseInt(address[1]);

			transportClient = transportClient.addTransportAddress((new InetSocketTransportAddress(InetAddress.getByAddress(ip), port)));
			// Node node =
			// NodeBuilder.nodeBuilder().settings(settings).client(true).node();
			// client = node.client();
		}

		IndexRequestBuilder indexRequestBuilder = transportClient.prepareIndex("hello", "hello", "567");
		XContentBuilder xContentBuilder = jsonBuilder().startObject();
		xContentBuilder.field("name", "zhuzhen");
		indexRequestBuilder.setSource(xContentBuilder.endObject()).get();
	}
}
