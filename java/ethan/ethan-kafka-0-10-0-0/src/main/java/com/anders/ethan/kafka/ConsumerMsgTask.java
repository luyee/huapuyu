package com.anders.ethan.kafka;

import java.util.List;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

import org.msgpack.MessagePack;

public class ConsumerMsgTask implements Runnable {
	private KafkaStream<byte[], byte[]> m_stream;
	private int m_threadNumber;

	public ConsumerMsgTask(KafkaStream<byte[], byte[]> stream, int threadNumber) {
		m_threadNumber = threadNumber;
		m_stream = stream;
	}

	public void run() {
		ConsumerIterator<byte[], byte[]> it = m_stream.iterator();
		while (it.hasNext()) {
			byte[] content = it.next().message();
			MessagePack messagePack = new MessagePack();
			messagePack.register(EventData.class);
			messagePack.register(Message.class);
				List<EventData> eventDatas = null;
				try {
					Message message = messagePack.read(content, Message.class);
					eventDatas = message.getDatas();
				} catch (Exception e) {
					e.printStackTrace();
				}
				for (EventData eventData : eventDatas) {
					System.out.println(eventData.getName());
				}
			
		}
		System.out.println("Shutting down Thread: " + m_threadNumber);
	}
}
