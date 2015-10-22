package com.anders.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.javaapi.producer.Producer;
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
}
