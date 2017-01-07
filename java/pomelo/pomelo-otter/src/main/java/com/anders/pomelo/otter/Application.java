package com.anders.pomelo.otter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.msgpack.MessagePack;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.otter.node.etl.load.loader.db.Message;
import com.alibaba.otter.shared.common.model.config.channel.ChannelParameter.SyncConsistency;
import com.alibaba.otter.shared.common.model.config.channel.ChannelParameter.SyncMode;
import com.alibaba.otter.shared.etl.model.EventColumn;
import com.alibaba.otter.shared.etl.model.EventData;
import com.alibaba.otter.shared.etl.model.EventType;

@SpringBootApplication
public class Application {

	// private static final Logger LOGGER = LoggerFactory
	// .getLogger(Application.class);

	public static void main(String[] args) throws IOException {
		SpringApplication.run(Application.class, args);

		// hbase
		// System.setProperty("hadoop.home.dir", "C:\\Users\\Anders\\opensource\\hadoop-common-2.6.0-bin");

		Configuration config1 = HBaseConfiguration.create();
		// config1.set("hbase.master", "192.168.56.101:16000");
		config1.set("hbase.zookeeper.property.clientPort", "2181");
		config1.set("hbase.zookeeper.quorum", "192.168.56.111");
		// config1.set("zookeeper.znode.parent","/hbase");

		// kafka
		Properties props = new Properties();
		props.put("zookeeper.connect", "192.168.56.111:2181");
		props.put("group.id", "hbase");
		props.put("serializer.class", "kafka.serializer.DefaultEncoder");
		props.put("key.serializer.class", "kafka.serializer.StringEncoder");
		// props.put("zookeeper.session.timeout.ms", "400");
		// props.put("zookeeper.sync.time.ms", "200");
		// props.put("auto.commit.interval.ms", "1000");
		ConsumerConfig config = new ConsumerConfig(props);

		ConsumerConnector consumer = Consumer.createJavaConsumerConnector(config);

		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put("my-test-topic", new Integer(1));

		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
		List<KafkaStream<byte[], byte[]>> streams = consumerMap.get("my-test-topic");

		ExecutorService executor = Executors.newSingleThreadExecutor();

		MessagePack messagePack = new MessagePack();
		messagePack.register(EventType.class);
		messagePack.register(EventColumn.class);
		messagePack.register(SyncMode.class);
		messagePack.register(SyncConsistency.class);
		messagePack.register(EventData.class);
		messagePack.register(Message.class);

		Connection connection = ConnectionFactory.createConnection(config1);

		for (final KafkaStream<byte[], byte[]> stream : streams) {
			executor.submit(new ConsumerTask(stream, connection, messagePack));
		}

		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
