package com.anders.ethan.kafka;

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

public class Recevie {
	public static void main(String[] args) {
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
		List<KafkaStream<byte[], byte[]>> streams = consumerMap.get("my-test-topic");

		// now launch all the threads
		ExecutorService executor = Executors.newFixedThreadPool(3);

		// now create an object to consume the messages
		//
		int threadNumber = 0;
		for (final KafkaStream<byte[], byte[]> stream : streams) {
			executor.submit(new ConsumerMsgTask(stream, threadNumber));
			threadNumber++;
		}

		try {
			Thread.sleep(100000000);
		} catch (InterruptedException ie) {

		}
	}
}
