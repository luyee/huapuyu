package com.anders.ethan.otter;

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
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;
import org.junit.Test;

public class Recevie {

	@Test
	public void test() throws IOException {
		System.setProperty("hadoop.home.dir",
				"C:\\Users\\Anders\\opensource\\hadoop-common-2.2.0-bin");

		Configuration config1 = HBaseConfiguration.create();
		// config1.set("hbase.master", "192.168.56.101:16000");
		config1.set("hbase.zookeeper.property.clientPort", "2181");
		config1.set("hbase.zookeeper.quorum",
				"192.168.56.101,192.168.56.102,192.168.56.103,192.168.56.104,192.168.56.105");
		// config1.set("zookeeper.znode.parent","/hbase");

		Connection connection = ConnectionFactory.createConnection(config1);

		Table table = connection.getTable(TableName.valueOf("tb_bank"));

		Properties props = new Properties();
		props.put(
				"zookeeper.connect",
				"192.168.56.101:2181,192.168.56.102:2181,192.168.56.103:2181,192.168.56.104:2181,192.168.56.105:2181");
		props.put("group.id", "group1");
		props.put("serializer.class", "kafka.serializer.DefaultEncoder");
		props.put("key.serializer.class", "kafka.serializer.StringEncoder");
		// props.put("zookeeper.session.timeout.ms", "400");
		// props.put("zookeeper.sync.time.ms", "200");
		// props.put("auto.commit.interval.ms", "1000");
		ConsumerConfig config = new ConsumerConfig(props);

		ConsumerConnector consumer = Consumer
				.createJavaConsumerConnector(config);

		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put("my-test-topic", new Integer(1));

		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer
				.createMessageStreams(topicCountMap);
		List<KafkaStream<byte[], byte[]>> streams = consumerMap
				.get("my-test-topic");

		// now launch all the threads
		 ExecutorService executor = Executors.newFixedThreadPool(3);

		// now create an object to consume the messages
		//
		 int threadNumber = 0;
		 for (final KafkaStream<byte[], byte[]> stream : streams) {
		 executor.submit(new ConsumerMsgTask(stream, threadNumber, table));
		 threadNumber++;
		 }

//		ConsumerIterator<byte[], byte[]> it = streams.get(0).iterator();
//		while (it.hasNext()) {
//			byte[] content = it.next().message();
//			MessagePack messagePack = new MessagePack();
//
//			messagePack.register(EventType.class);
//			messagePack.register(EventColumn.class);
//			messagePack.register(SyncMode.class);
//			messagePack.register(SyncConsistency.class);
//			messagePack.register(EventData.class);
//			messagePack.register(Message.class);
//
//			List<EventData> eventDatas = null;
//			try {
//				Message message = messagePack.read(content, Message.class);
//				eventDatas = message.getEventDatas();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			if (eventDatas != null) {
//				for (EventData eventData : eventDatas) {
//					if (eventData.getEventType().isInsert()) {
//						List<EventColumn> eventColumns = eventData.getKeys();
//						Put put = new Put(Bytes.toBytes(eventColumns.get(0)
//								.getColumnValue()));
//
//						System.out.println(eventColumns.get(0).getColumnName()
//								+ " " + eventColumns.get(0).getColumnValue());
//
//						eventColumns = eventData.getColumns();
//						for (EventColumn eventColumn : eventColumns) {
//							put.addColumn(Bytes.toBytes("cf"),
//									Bytes.toBytes(eventColumn.getColumnName()),
//									Bytes.toBytes(eventColumn.getColumnValue()));
//
//							System.out.println(eventColumn.getColumnName()
//									+ " " + eventColumn.getColumnValue());
//						}
//						try {
//							table.put(put);
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//					}
//
//					System.out.println(eventData.getSql());
//				}
//			}
//		}

		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
