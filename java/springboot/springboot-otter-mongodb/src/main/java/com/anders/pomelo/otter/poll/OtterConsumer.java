package com.anders.pomelo.otter.poll;

import java.util.ArrayList;
import java.util.List;

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
import com.anders.pomelo.otter.cfg.KafkaProps;
import com.anders.pomelo.otter.cfg.MongoProps;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

@Component
public class OtterConsumer implements InitializingBean, DisposableBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(OtterConsumer.class);

	@Autowired
	private KafkaProps KafkaProps;
	@Autowired
	private MongoProps mongoProps;

	private MongoClient mongoClient;
	private MongoDatabase mongoDatabase;

	@Override
	public void afterPropertiesSet() throws Exception {
		LOGGER.debug("mongo.address : {}", mongoProps.getAddress());

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

		List<ServerAddress> serverAddresses = new ArrayList<ServerAddress>();
		String[] addresses = mongoProps.getAddress().split(",");
		for (String address : addresses) {
			String[] host = address.split(":");
			serverAddresses.add(new ServerAddress(host[0], Integer.parseInt(host[1])));
		}

		MongoClientOptions.Builder build = new MongoClientOptions.Builder();
		build.connectionsPerHost(50);
		// build.autoConnectRetry(true);
		build.threadsAllowedToBlockForConnectionMultiplier(50);
		build.maxWaitTime(1000 * 60 * 2);
		build.connectTimeout(1000 * 60 * 1);

		MongoClientOptions myOptions = build.build();

		mongoClient = new MongoClient(serverAddresses, myOptions);
		mongoDatabase = mongoClient.getDatabase(mongoProps.getDatabase());

		ConsumerThread consumer = new ConsumerThread(KafkaProps, mongoDatabase, messagePack);
		consumer.start();
	}

	@Override
	public void destroy() throws Exception {
		if (mongoClient != null) {
			mongoClient.close();
		}
	}
}
