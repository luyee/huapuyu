package com.anders.pomelo.otter.poll;

import java.net.InetAddress;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
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

		Settings settings = Settings.builder().put("cluster.name", esProps.getClusterName()).put("client.transport.sniff", true).build();

		String[] hosts = esProps.getHost().split(",");

		client = TransportClient.builder().settings(settings).build();
		for (String host : hosts) {
			String[] address = host.split(":");
			String[] ipStrs = address[0].split("\\.");
			byte[] ip = new byte[4];
			for (int i = 0; i < 4; i++) {
				ip[i] = (byte) (Integer.parseInt(ipStrs[i]) & 0xff);
			}
			int port = Integer.parseInt(address[1]);

			client = client.addTransportAddress((new InetSocketTransportAddress(InetAddress.getByAddress(ip), port)));
			// Node node = NodeBuilder.nodeBuilder().settings(settings).client(true).node();
			// client = node.client();
		}

		ConsumerThread consumer = new ConsumerThread(KafkaProps, client, messagePack);
		consumer.start();
	}

	@Override
	public void destroy() throws Exception {
		if (client != null) {
			client.close();
		}
	}
}
