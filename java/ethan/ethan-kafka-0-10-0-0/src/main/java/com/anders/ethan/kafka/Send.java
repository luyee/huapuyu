package com.anders.ethan.kafka;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.msgpack.MessagePack;

public class Send {

	public static void main(String[] args) throws IOException {
		List<EventData> datas = new ArrayList<EventData>();

		EventData eventData;
		for (int i = 1; i <= 10; i++) {
			eventData = new EventData();
			eventData.setName("zhuzhen" + i);
			datas.add(eventData);
		}

		Message message = new Message();
		message.setDatas(datas);

		sendToKafka(message);

		System.out.println("ok");
		System.in.read();
	}

	private static void sendToKafka(Message message) throws IOException {
		MessagePack messagePack = new MessagePack();
		messagePack.register(EventData.class);
		messagePack.register(Message.class);
		byte[] content = messagePack.write(message);

		// Properties props = new Properties();
		// props.put("serializer.class", "kafka.serializer.DefaultEncoder");
		// props.put("metadata.broker.list",
		// "192.168.56.102:9092,192.168.56.103:9092,192.168.56.104:9092,192.168.56.105:9092,192.168.56.106:9092");
		// props.put("key.serializer.class", "kafka.serializer.StringEncoder");
		// // props.put("partitioner.class",
		// "com.anders.kafka.PartitionerDemo");
		// props.put("request.required.acks", "1");
		// ProducerConfig config = new ProducerConfig(props);

		Properties props = new Properties();
		props.put("bootstrap.servers",
				"192.168.56.101:6667,192.168.56.101:6667,192.168.56.101:6667");
		props.put("client.id", "DemoProducer");
		props.put("key.serializer",
				"org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer",
				"org.apache.kafka.common.serialization.ByteArraySerializer");
//		props.put("value.serializer",
//				"org.apache.kafka.common.serialization.StringSerializer");
		KafkaProducer<String, byte[]> producer = new KafkaProducer<String, byte[]>(
				props);
//		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(
//				props);

		// Producer<String, byte[]> producer = new Producer<String,
		// byte[]>(config);
		// KeyedMessage<String, byte[]> data = new KeyedMessage<String,
		// byte[]>("my-test-topic", content);
		// try {
		// producer.send(data);
		// } catch (Exception e) {
		// e.printStackTrace();
		// } finally {
		// try {
		// producer.close();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }

		try {
			producer.send(new ProducerRecord<String, byte[]>(
					"zhuzhen-test-topic", content));
//			producer.send(new ProducerRecord<String, String>(
//					"zhuzhen-test-topic", "hello world"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			producer.close();
		}
	}

}

