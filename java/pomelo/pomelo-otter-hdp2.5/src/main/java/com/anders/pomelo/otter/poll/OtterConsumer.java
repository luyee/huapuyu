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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.otter.node.etl.load.loader.db.Message;
import com.alibaba.otter.shared.common.model.config.channel.ChannelParameter.SyncConsistency;
import com.alibaba.otter.shared.common.model.config.channel.ChannelParameter.SyncMode;
import com.alibaba.otter.shared.etl.model.EventColumn;
import com.alibaba.otter.shared.etl.model.EventData;
import com.alibaba.otter.shared.etl.model.EventType;
import com.anders.pomelo.otter.cfg.HBaseProps;
import com.anders.pomelo.otter.cfg.KafkaProps;

@Component
public class OtterConsumer implements InitializingBean, DisposableBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(OtterConsumer.class);

	@Autowired
	private KafkaProps kafkaProps;
	@Autowired
	private HBaseProps hBaseProps;

	private Connection connection;
	private Admin admin;

	@Override
	public void afterPropertiesSet() throws Exception {
		LOGGER.error("hbase.zk.port : {}", hBaseProps.getZkProps().getPort());
		LOGGER.error("hbase.zk.host : {}", hBaseProps.getZkProps().getHost());

		LOGGER.error("kafka.brokers : {}", kafkaProps.getBrokers());
		LOGGER.error("kafka.groupId : {}", kafkaProps.getGroupId());
		LOGGER.error("kafka.topic : {}", kafkaProps.getTopic());
		LOGGER.error("kafka.sessionTimeoutMs : {}", kafkaProps.getSessionTimeoutMs());
		LOGGER.error("kafka.enableAutoCommit : {}", kafkaProps.getEnableAutoCommit());
		LOGGER.error("kafka.autoCommitIntervalMs : {}", kafkaProps.getAutoCommitIntervalMs());
		LOGGER.error("kafka.maxPollRecords : {}", kafkaProps.getMaxPollRecords());

		MessagePack messagePack = new MessagePack();
		messagePack.register(EventType.class);
		messagePack.register(EventColumn.class);
		messagePack.register(SyncMode.class);
		messagePack.register(SyncConsistency.class);
		messagePack.register(EventData.class);
		messagePack.register(Message.class);

		// TODO Anders 以下需要注释掉
		// System.setProperty("hadoop.home.dir", "C:\\Users\\Anders\\opensource\\hadoop-common-2.6.0-bin");

		// hbase
		Configuration hbaseCfg = HBaseConfiguration.create();
		// hbaseCfg.set("hbase.master", "192.168.56.101:16000");
		hbaseCfg.set("hbase.zookeeper.property.clientPort", hBaseProps.getZkProps().getPort());
		hbaseCfg.set("hbase.zookeeper.quorum", hBaseProps.getZkProps().getHost());
		hbaseCfg.set("zookeeper.znode.parent", hBaseProps.getZkProps().getParent());
		// hbaseCfg.set("zookeeper.znode.parent","/hbase");

		connection = ConnectionFactory.createConnection(hbaseCfg);
		admin = connection.getAdmin();

		ConsumerThread consumer = new ConsumerThread(kafkaProps, connection, admin, messagePack);
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
