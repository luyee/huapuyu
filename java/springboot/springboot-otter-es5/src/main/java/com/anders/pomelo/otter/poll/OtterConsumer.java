package com.anders.pomelo.otter.poll;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.msgpack.MessagePack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.otter.node.etl.load.loader.db.Message;
import com.alibaba.otter.shared.common.model.config.channel.ChannelParameter.SyncConsistency;
import com.alibaba.otter.shared.common.model.config.channel.ChannelParameter.SyncMode;
import com.alibaba.otter.shared.etl.model.EventColumn;
import com.alibaba.otter.shared.etl.model.EventData;
import com.alibaba.otter.shared.etl.model.EventType;
import com.anders.pomelo.otter.cfg.EsProps;
import com.anders.pomelo.otter.cfg.KafkaProps;

@Component
public class OtterConsumer implements InitializingBean, DisposableBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(OtterConsumer.class);

	@Autowired
	private KafkaProps KafkaProps;
	@Autowired
	private EsProps esProps;

	private TransportClient client;

	// private Client client;

	@Override
	public void afterPropertiesSet() throws Exception {
		LOGGER.debug("es.clusterName : {}", esProps.getClusterName());
		LOGGER.debug("es.host : {}", esProps.getHost());
		LOGGER.debug("es.index : {}", esProps.getIndex());
		LOGGER.debug("es.username : {}", esProps.getUsername());

		LOGGER.debug("kafka.brokers : {}", KafkaProps.getBrokers());
		LOGGER.debug("kafka.groupId : {}", KafkaProps.getGroupId());
		LOGGER.debug("kafka.topic : {}", KafkaProps.getTopic());
		LOGGER.debug("kafka.sessionTimeoutMs : {}", KafkaProps.getSessionTimeoutMs());
		LOGGER.debug("kafka.enableAutoCommit : {}", KafkaProps.getEnableAutoCommit());
		LOGGER.debug("kafka.autoCommitIntervalMs : {}", KafkaProps.getAutoCommitIntervalMs());
		LOGGER.debug("kafka.maxPollRecords : {}", KafkaProps.getMaxPollRecords());

		MessagePack messagePack = new MessagePack();
		messagePack.register(EventType.class);
		messagePack.register(EventColumn.class);
		messagePack.register(SyncMode.class);
		messagePack.register(SyncConsistency.class);
		messagePack.register(EventData.class);
		messagePack.register(Message.class);

		// Settings settings = Settings.builder().put("cluster.name",
		// esProps.getClusterName()).put("client.transport.sniff",
		// false).build();
		Settings.Builder builder = Settings.builder().put("cluster.name", esProps.getClusterName());
		if (StringUtils.isNotBlank(esProps.getUsername())) {
			builder.put("xpack.security.user", esProps.getUsername() + ":" + esProps.getPassword());
		}

		String[] hosts = esProps.getHost().split(",");

		client = TransportClient.builder().settings(builder.build()).build();
		for (String host : hosts) {
			String[] address = host.split(":");
			String[] ipStrs = address[0].split("\\.");
			byte[] ip = new byte[4];
			for (int i = 0; i < 4; i++) {
				ip[i] = (byte) (Integer.parseInt(ipStrs[i]) & 0xff);
			}
			int port = Integer.parseInt(address[1]);

			client = client.addTransportAddress((new InetSocketTransportAddress(InetAddress.getByAddress(ip), port)));
			// Node node =
			// NodeBuilder.nodeBuilder().settings(settings).client(true).node();
			// client = node.client();
		}

		ConsumerThread consumer = new ConsumerThread(KafkaProps, client, messagePack, esProps.getIndex());
		consumer.start();
	}

	@Override
	public void destroy() throws Exception {
		if (client != null) {
			client.close();
		}
	}

	public static void main(String[] args) throws IOException {
		Settings.Builder builder = Settings.builder().put("cluster.name", "elasticsearch");
		builder.put("xpack.security.user", "elastic:changeme");

		TransportClient client = TransportClient.builder().settings(builder.build()).build();
		String[] ipStrs = "192.168.56.121".split("\\.");
		byte[] ip = new byte[4];
		for (int i = 0; i < 4; i++) {
			ip[i] = (byte) (Integer.parseInt(ipStrs[i]) & 0xff);
		}
		client = client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByAddress(ip), 9300));
		IndexRequestBuilder indexRequestBuilder = client.prepareIndex("zhuzhen-test", "zhuzhen", "123");
		XContentBuilder xContentBuilder = jsonBuilder().startObject();
		xContentBuilder.field("age", (new Date()).getTime());
		indexRequestBuilder.setSource(xContentBuilder.endObject()).get();
	}
}
