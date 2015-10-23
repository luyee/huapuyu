package com.anders.kafka;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kafka.api.FetchRequest;
import kafka.api.FetchRequestBuilder;
import kafka.api.PartitionOffsetRequestInfo;
import kafka.cluster.Broker;
import kafka.common.TopicAndPartition;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.FetchResponse;
import kafka.javaapi.OffsetRequest;
import kafka.javaapi.OffsetResponse;
import kafka.javaapi.PartitionMetadata;
import kafka.javaapi.TopicMetadata;
import kafka.javaapi.TopicMetadataRequest;
import kafka.javaapi.TopicMetadataResponse;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.javaapi.producer.Producer;
import kafka.message.MessageAndOffset;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import org.junit.Test;

// 参考kafka例子jar
public class ClientTest {
	@Test
	public void producer() {
		Random rnd = new Random();
		int events = 100;

		Properties props = new Properties();
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		// props.put("metadata.broker.list",
		// "localhost:9092");lu1:9092,lu2:9092,lu3:9092
		props.put("metadata.broker.list", "lu1:9092,lu2:9092,lu3:9092");
		// key.serializer.class默认为serializer.class
		props.put("key.serializer.class", "kafka.serializer.StringEncoder");
		// 可选配置，如果不配置，则使用默认的partitioner
		props.put("partitioner.class", "com.anders.kafka.PartitionerDemo");
		// 触发acknowledgement机制，否则是fire and forget，可能会引起数据丢失
		// 值为0,1,-1,可以参考
		// http://kafka.apache.org/08/configuration.html
		props.put("request.required.acks", "1");
		// props.put("zookeeper.session.timeout.ms", "400000");
		ProducerConfig config = new ProducerConfig(props);

		// 创建producer
		Producer<String, String> producer = new Producer<String, String>(config);
		// 产生并发送消息
		long start = System.currentTimeMillis();
		for (long i = 0; i < events; i++) {
			// long runtime = new Date().getTime();
			// String ip = "192.168.2." + i;// rnd.nextInt(255);
			// String msg = runtime + ",www.example.com," + ip;
			// 如果topic不存在，则会自动创建，默认replication-factor为1，partitions为0
			KeyedMessage<String, String> data = new KeyedMessage<String, String>(
					"anders1", i + "", i + "\thelloworld");
			producer.send(data);
		}
		System.out.println("耗时:" + (System.currentTimeMillis() - start));
		// 关闭producer
		producer.close();
	}

	@Test
	public void consumer() {
		Properties props = new Properties();
		props.put("zookeeper.connect", "anders1:2181,anders2:2181,anders3:2181");
		props.put("group.id", "group1");
		// props.put("zookeeper.session.timeout.ms", "400");
		// props.put("zookeeper.sync.time.ms", "200");
		// props.put("auto.commit.interval.ms", "1000");
		ConsumerConfig config = new ConsumerConfig(props);

		ConsumerConnector consumer = Consumer
				.createJavaConsumerConnector(config);

		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put("anders1", new Integer(3));
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer
				.createMessageStreams(topicCountMap);
		List<KafkaStream<byte[], byte[]>> streams = consumerMap.get("anders1");

		// now launch all the threads
		ExecutorService executor = Executors.newFixedThreadPool(3);

		// now create an object to consume the messages
		//
		int threadNumber = 0;
		for (final KafkaStream stream : streams) {
			executor.submit(new ConsumerMsgTask(stream, threadNumber));
			threadNumber++;
		}

		try {
			Thread.sleep(100000000);
		} catch (InterruptedException ie) {

		}
	}

	@Test
	public void consumer1() throws Exception {

		String topic = "anders1";
		int partition = 1;
		String brokers = "lu1:9092";
		int maxReads = 100; // 读多少条数据
		// 1.找leader
		PartitionMetadata metadata = null;
		for (String ipPort : brokers.split(",")) {
			// 我们无需要把所有的brokers列表加进去，目的只是为了获得metedata信息，故只要有broker可连接即可
			SimpleConsumer consumer = null;
			try {
				String[] ipPortArray = ipPort.split(":");
				consumer = new SimpleConsumer(ipPortArray[0],
						Integer.parseInt(ipPortArray[1]), 100000, 64 * 1024,
						"leaderLookup");
				List<String> topics = new ArrayList<String>();
				topics.add(topic);
				TopicMetadataRequest req = new TopicMetadataRequest(topics);
				// 取meta信息
				TopicMetadataResponse resp = consumer.send(req);

				// 获取topic的所有metedate信息(目测只有一个metedata信息，何来多个？)
				List<TopicMetadata> metaData = resp.topicsMetadata();
				for (TopicMetadata item : metaData) {
					for (PartitionMetadata part : item.partitionsMetadata()) {
						// 获取每个meta信息的分区信息,这里我们只取我们关心的partition的metedata
						System.out.println("----" + part.partitionId());
						if (part.partitionId() == partition) {
							metadata = part;
							break;
						}
					}
				}
			} catch (Exception e) {
				System.out.println("Error communicating with Broker [" + ipPort
						+ "] to find Leader for [" + topic + ", " + partition
						+ "] Reason: " + e);
			} finally {
				if (consumer != null)
					consumer.close();
			}
		}
		if (metadata == null || metadata.leader() == null) {
			System.out.println("meta data or leader not found, exit.");
			return;
		}
		// 拿到leader
		Broker leadBroker = metadata.leader();
		// 获取所有副本
		System.out.println(metadata.replicas());
		// 2.获取lastOffset(这里提供了两种方式：从头取或从最后拿到的开始取，下面这个是从头取)
		long whichTime = kafka.api.OffsetRequest.EarliestTime();
		// 这个是从最后拿到的开始取
		// long whichTime = kafka.api.OffsetRequest.LatestTime();
		System.out.println("lastTime:" + whichTime);
		String clientName = "Client_" + topic + "_" + partition;
		SimpleConsumer consumer = new SimpleConsumer(leadBroker.host(),
				leadBroker.port(), 100000, 64 * 1024, clientName);
		TopicAndPartition topicAndPartition = new TopicAndPartition(topic,
				partition);
		Map<TopicAndPartition, PartitionOffsetRequestInfo> requestInfo = new HashMap<TopicAndPartition, PartitionOffsetRequestInfo>();
		requestInfo.put(topicAndPartition, new PartitionOffsetRequestInfo(
				whichTime, 1));
		OffsetRequest request = new OffsetRequest(requestInfo,
				kafka.api.OffsetRequest.CurrentVersion(), clientName);
		// 获取指定时间前有效的offset列表
		OffsetResponse response = consumer.getOffsetsBefore(request);
		if (response.hasError()) {
			System.out
					.println("Error fetching data Offset Data the Broker. Reason: "
							+ response.errorCode(topic, partition));
			return;
		}
		// 千万不要认为offset一定是从0开始的
		long[] offsets = response.offsets(topic, partition);
		System.out.println("offset list:" + Arrays.toString(offsets));
		long offset = offsets[0];

		// 读数据
		while (maxReads > 0) {
			// 注意不要调用里面的replicaId()方法，这是内部使用的。
			FetchRequest req = new FetchRequestBuilder().clientId(clientName)
					.addFetch(topic, partition, offset, 100000).build();
			FetchResponse fetchResponse = consumer.fetch(req);
			if (fetchResponse.hasError()) {
				// 出错处理。这里只直接返回了。实际上可以根据出错的类型进行判断，如code ==
				// ErrorMapping.OffsetOutOfRangeCode()表示拿到的offset错误
				// 一般出错处理可以再次拿offset,或重新找leader，重新建立consumer。可以将上面的操作都封装成方法。再在该循环来进行消费
				// 当然，在取所有leader的同时可以用metadata.replicas()更新最新的节点信息。另外zookeeper可能不会立即检测到有节点挂掉，故如果发现老的leader和新的leader一样，可能是leader根本没挂，也可能是zookeeper还没检测到，总之需要等等。
				short code = fetchResponse.errorCode(topic, partition);
				System.out.println("Error fetching data from the Broker:"
						+ leadBroker + " Reason: " + code);
				return;
			}
			// 取一批消息
			boolean empty = true;
			for (MessageAndOffset messageAndOffset : fetchResponse.messageSet(
					topic, partition)) {
				empty = false;
				long curOffset = messageAndOffset.offset();
				// 下面这个检测有必要，因为当消息是压缩的时候，通过fetch获取到的是一个整块数据。块中解压后不一定第一个消息就是offset所指定的。就是说存在再次取到已读过的消息。
				if (curOffset < offset) {
					System.out.println("Found an old offset: " + curOffset
							+ " Expecting: " + offset);
					continue;
				}
				// 可以通过当前消息知道下一条消息的offset是多少
				offset = messageAndOffset.nextOffset();
				ByteBuffer payload = messageAndOffset.message().payload();
				byte[] bytes = new byte[payload.limit()];
				payload.get(bytes);
				System.out.println(String.valueOf(messageAndOffset.offset())
						+ ": " + new String(bytes, "UTF-8"));
				maxReads++;
			}
			// 进入循环中，等待一会后获取下一批数据
			if (empty) {
				Thread.sleep(1000);
			}
		}
		// 退出（这里象征性的写一下）
		if (consumer != null)
			consumer.close();

	}
}
