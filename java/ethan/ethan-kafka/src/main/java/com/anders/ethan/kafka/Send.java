package com.anders.ethan.kafka;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.collections4.CollectionUtils;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class Send {

	public static void main(String[] args) {
		List<EventData> datas = new ArrayList<EventData>();

		EventData eventData;
		for (int i = 1; i <= 10; i++) {
			eventData = new EventData();
			eventData.setName("zhuzhen" + i);
			datas.add(eventData);
		}

		sendToKafka(datas);
	}

	private static void sendToKafka(List<EventData> datas) {
		if (CollectionUtils.isEmpty(datas)) {
			return;
		}

		Properties props = new Properties();
		props.put("serializer.class", "kafka.serializer.DefaultEncoder");
		props.put("metadata.broker.list",
				"192.168.56.102:9092,192.168.56.103:9092,192.168.56.104:9092,192.168.56.105:9092,192.168.56.106:9092");
		props.put("key.serializer.class", "kafka.serializer.StringEncoder");
		// props.put("partitioner.class", "com.anders.kafka.PartitionerDemo");
		props.put("request.required.acks", "1");
		ProducerConfig config = new ProducerConfig(props);

		Producer<String, List<EventData>> producer = new Producer<String, List<EventData>>(config);
		KeyedMessage<String, List<EventData>> data = new KeyedMessage<String, List<EventData>>("my-test-topic", datas);
		try {
			producer.send(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				producer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
