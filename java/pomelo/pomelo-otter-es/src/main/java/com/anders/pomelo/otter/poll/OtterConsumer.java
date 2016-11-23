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
import com.anders.pomelo.otter.cfg.ESProps;
import com.anders.pomelo.otter.cfg.KafkaProps;

@Component
public class OtterConsumer implements InitializingBean, DisposableBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(OtterConsumer.class);

	@Autowired
	private KafkaProps KafkaProps;
	@Autowired
	private ESProps esProps;

	private TransportClient client;

	// private Client client;

	@Override
	public void afterPropertiesSet() throws Exception {
		LOGGER.error("es.clusterName : {}", esProps.getClusterName());

		LOGGER.error("kafka.brokers : {}", KafkaProps.getBrokers());
		LOGGER.error("kafka.groupId : {}", KafkaProps.getGroupId());
		LOGGER.error("kafka.topic : {}", KafkaProps.getTopic());
		LOGGER.error("kafka.sessionTimeoutMs : {}", KafkaProps.getSessionTimeoutMs());
		LOGGER.error("kafka.enableAutoCommit : {}", KafkaProps.getEnableAutoCommit());
		LOGGER.error("kafka.autoCommitIntervalMs : {}", KafkaProps.getAutoCommitIntervalMs());
		LOGGER.error("kafka.maxPollRecords : {}", KafkaProps.getMaxPollRecords());

		MessagePack messagePack = new MessagePack();
		messagePack.register(EventType.class);
		messagePack.register(EventColumn.class);
		messagePack.register(SyncMode.class);
		messagePack.register(SyncConsistency.class);
		messagePack.register(EventData.class);
		messagePack.register(Message.class);

		// TODO Anders 以下需要注释掉
		// System.setProperty("hadoop.home.dir",
		// "C:\\Users\\Anders\\opensource\\hadoop-common-2.6.0-bin");

		Settings settings = Settings.builder().put("cluster.name", esProps.getClusterName()).put("client.transport.sniff", true).build();

		// TODO Anders 这块代码要改写下
		byte[] address = { (byte) 172, (byte) 16, (byte) 57, (byte) 143 };
		client = TransportClient.builder().settings(settings).build().addTransportAddress((new InetSocketTransportAddress(InetAddress.getByAddress(address), 9300)));
		// Node node = NodeBuilder.nodeBuilder().settings(settings).client(true).node();
		// client = node.client();

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
