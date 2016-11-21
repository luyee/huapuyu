package com.anders.pomelo.otter.poll;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.msgpack.MessagePack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.otter.node.etl.load.loader.db.Message;
import com.alibaba.otter.shared.common.model.config.channel.ChannelParameter.SyncConsistency;
import com.alibaba.otter.shared.common.model.config.channel.ChannelParameter.SyncMode;
import com.alibaba.otter.shared.etl.model.EventColumn;
import com.alibaba.otter.shared.etl.model.EventData;
import com.alibaba.otter.shared.etl.model.EventType;

@Component
public class OtterConsumer implements InitializingBean, DisposableBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(OtterConsumer.class);

	// hbase
	@Value("${hbase.zk.port}")
	private String hbaseZkPort = "2181";
	@Value("${hbase.zk.host}")
	private String hbaseZkHost;
	@Value("${hbase.zk.parent}")
	private String hbaseZkParent = "/hbase";

	// kafka
	@Value("${kafka.brokers}")
	private String kafkaBrokers;
	@Value("${kafka.group.id}")
	private String kafkaGroupId = "hbaseConsumer";
	@Value("${kafka.topic}")
	private String kafkaTopic;

	private Connection connection;
	private Admin admin;

	@Override
	public void afterPropertiesSet() throws Exception {
		LOGGER.debug("hbase zk port : {}", hbaseZkPort);
		LOGGER.debug("hbase zk host : {}", hbaseZkHost);
		LOGGER.debug("kafka brokers : {}", kafkaBrokers);
		LOGGER.debug("kafka group id : {}", kafkaGroupId);
		LOGGER.debug("kafka topic : {}", kafkaTopic);

		MessagePack messagePack = new MessagePack();
		messagePack.register(EventType.class);
		messagePack.register(EventColumn.class);
		messagePack.register(SyncMode.class);
		messagePack.register(SyncConsistency.class);
		messagePack.register(EventData.class);
		messagePack.register(Message.class);

		// hbase
		System.setProperty("hadoop.home.dir", "C:\\Users\\Anders\\opensource\\hadoop-common-2.6.0-bin");

		// hbase
		Configuration hbaseCfg = HBaseConfiguration.create();
		// hbaseCfg.set("hbase.master", "192.168.56.101:16000");
		hbaseCfg.set("hbase.zookeeper.property.clientPort", hbaseZkPort);
		hbaseCfg.set("hbase.zookeeper.quorum", hbaseZkHost);
		// hbaseCfg.set("zookeeper.znode.parent","/hbase");
		hbaseCfg.set("zookeeper.znode.parent", hbaseZkParent);

		connection = ConnectionFactory.createConnection(hbaseCfg);
		admin = connection.getAdmin();

		Consumer consumer = new Consumer(kafkaBrokers, kafkaGroupId, kafkaTopic, connection, admin, messagePack);
		consumer.start();
	}

	@Override
	public void destroy() throws Exception {
		if (admin != null) {
			admin.close();
		}

		if (connection != null) {
			connection.close();
		}
	}
}
