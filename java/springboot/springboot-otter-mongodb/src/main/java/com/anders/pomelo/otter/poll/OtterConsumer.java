package com.anders.pomelo.otter.poll;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
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
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;

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
		LOGGER.debug("mongo.database : {}", mongoProps.getDatabase());
		LOGGER.debug("mongo.username : {}", mongoProps.getUsername());

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

		// MongoClientOptions.Builder build = new MongoClientOptions.Builder();
		// build.connectionsPerHost(50);
		// build.autoConnectRetry(true);
		// build.threadsAllowedToBlockForConnectionMultiplier(50);
		// build.maxWaitTime(1000 * 60 * 2);
		// build.connectTimeout(1000 * 60 * 1);
		// MongoClientOptions myOptions = build.build();

		// mongoClient = new MongoClient(serverAddresses, myOptions);

		if (StringUtils.isBlank(mongoProps.getUsername())) {
			mongoClient = new MongoClient(serverAddresses);
		} else {
			MongoCredential credentials = MongoCredential.createCredential(mongoProps.getUsername(), mongoProps.getDatabase(), mongoProps.getPassword().toCharArray());
			List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();
			credentialsList.add(credentials);

			mongoClient = new MongoClient(serverAddresses, credentialsList);
		}
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

	public static void main(String[] args) throws IOException {
		MongoCredential credentials = MongoCredential.createCredential("u_databus", "eif_market", "DataBUSPW".toCharArray());
		List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();
		credentialsList.add(credentials);

		MongoClient mongoClient = new MongoClient(new ServerAddress("172.16.59.119", 47017), credentialsList);
		MongoDatabase mongoDatabase = mongoClient.getDatabase("eif_market");
		MongoCollection<Document> collection = mongoDatabase.getCollection("test");
		Document doc = new Document();
		doc.put("_id", "45678910");
		doc.put("now", new Date());

		Bson filter = Filters.eq("_id", "45678910");
		// collection.insertOne(doc);
		UpdateOptions options = new UpdateOptions();
		options.upsert(true);
		collection.updateOne(filter, new Document("$set", doc), options);

		FindIterable<Document> iter = collection.find(new Document("_id", "4567"));
		iter.forEach(new Block<Document>() {

			@Override
			public void apply(Document t) {
				Date date = (Date) t.get("now");

				System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
			}

		});

		mongoClient.close();
	}
}
