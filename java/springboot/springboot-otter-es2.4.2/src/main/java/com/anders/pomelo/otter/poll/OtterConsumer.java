package com.anders.pomelo.otter.poll;

import java.net.InetAddress;

import org.apache.commons.lang.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
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
import com.anders.pomelo.otter.cfg.ZkProps;

@Component
public class OtterConsumer implements InitializingBean, DisposableBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(OtterConsumer.class);

	@Autowired
	private KafkaProps KafkaProps;
	@Autowired
	private EsProps esProps;
	@Autowired
	private ZkProps zkProps;

	private TransportClient transportClient;
	private LeaderLatch leaderLatch;
	private CuratorFramework curatorFramework;
	private ConsumerThread consumerThread;

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

		LOGGER.debug("zk.address : {}", zkProps.getAddress());
		LOGGER.debug("zk.node : {}", zkProps.getNode());

		MessagePack messagePack = new MessagePack();
		messagePack.register(EventType.class);
		messagePack.register(EventColumn.class);
		messagePack.register(SyncMode.class);
		messagePack.register(SyncConsistency.class);
		messagePack.register(EventData.class);
		messagePack.register(Message.class);

		Settings settings = Settings.builder().put("cluster.name", esProps.getClusterName()).build();
		if (StringUtils.isNotBlank(esProps.getUsername())) {
			// TODO Anders 增加权限
		}

		String[] hosts = esProps.getHost().split(",");

		transportClient = TransportClient.builder().settings(settings).build();
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

		// curatorFramework =
		// CuratorFrameworkFactory.builder().connectString(zkProps.getAddress()).retryPolicy(new
		// ExponentialBackoffRetry(1000, 3)).sessionTimeoutMs(5000).build();
		curatorFramework = CuratorFrameworkFactory.builder().connectString(zkProps.getAddress()).retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
		leaderLatch = new LeaderLatch(curatorFramework, zkProps.getNode());
		leaderLatch.addListener(new LeaderLatchListener() {
			@Override
			public void isLeader() {
				LOGGER.debug("is leader");
				consumerThread = new ConsumerThread(KafkaProps, transportClient, messagePack, esProps.getIndex());
				consumerThread.start();
			}

			@Override
			public void notLeader() {
				LOGGER.debug("not leader");
				if (consumerThread != null) {
					consumerThread.shutdown();
				}
			}
		});

		curatorFramework.start();
		leaderLatch.start();
	}

	@Override
	public void destroy() throws Exception {
		if (consumerThread != null) {
			consumerThread.shutdown();
		}
		if (transportClient != null) {
			transportClient.close();
		}
		if (leaderLatch != null) {
			CloseableUtils.closeQuietly(leaderLatch);
		}
		if (curatorFramework != null) {
			CloseableUtils.closeQuietly(curatorFramework);
		}
	}
}
