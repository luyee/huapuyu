package com.anders.ethan.es.sql;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.ElasticSearchDruidDataSourceFactory;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class SqlTest {

	public void test() throws Exception {
		Properties properties = new Properties();
		properties.put("url",
				"jdbc:elasticsearch://192.168.56.101:9300/ni-database-zhuzhen");
		DruidDataSource dds = (DruidDataSource) ElasticSearchDruidDataSourceFactory
				.createDataSource(properties);
		Connection connection = dds.getConnection();
		PreparedStatement ps = connection
				.prepareStatement("SELECT * from ni-database-zhuzhen where empname='emp11'");
		ResultSet resultSet = ps.executeQuery();
		List<String> result = new ArrayList<String>();
		while (resultSet.next()) {
			System.out.println(resultSet.getString("empname"));
		}
		ps.close();
		connection.close();
		dds.close();
	}

	@Test
	public void test1() throws Exception {
		Settings settings = Settings.builder()
				.put("cluster.name", "elasticsearch").build();

		TransportClient client = TransportClient.builder().settings(settings)
				.build();

		try {
			String[] ipStrs = "172.16.1.29".split("\\.");
			byte[] ip = new byte[4];
			for (int i = 0; i < 4; i++) {
				ip[i] = (byte) (Integer.parseInt(ipStrs[i]) & 0xff);
			}

			client = client
					.addTransportAddress((new InetSocketTransportAddress(
							InetAddress.getByAddress(ip), 9300)));
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
			rangeTimeQuery(client);
		}
	}

	private void rangeTimeQuery(TransportClient client) throws ParseException {
		String from = "2020-02-13 00:00:00";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date fromDate = simpleDateFormat.parse(from);

		RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(
				"expripration_time").gte(fromDate.getTime());

		long begin = new Date().getTime();
		SearchResponse searchResponse = client.prepareSearch("eif_market")
				.setTypes("t_market_coupon_user").setQuery(rangeQueryBuilder)
				.setSize(10000).execute().actionGet();
		System.out.println("count : "
				+ searchResponse.getHits().getHits().length + "; time : "
				+ (new Date().getTime() - begin));

//		for (int i = 0; i < searchResponse.getHits().getHits().length; i++) {
//			System.out.println(searchResponse.getHits().getHits()[i]
//					.getSourceAsString());
//		}
	}

}
