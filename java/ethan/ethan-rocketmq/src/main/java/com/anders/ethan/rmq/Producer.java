package com.anders.ethan.rmq;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

public class Producer {
	public static void main(String[] args)
			throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
		DefaultMQProducer producer = new DefaultMQProducer("rmq_test123");
		// producer.setNamesrvAddr("192.168.56.121:9876");
		producer.setNamesrvAddr(
				"192.168.56.101:9876;192.168.56.102:9876;192.168.56.103:9876;192.168.56.104:9876;192.168.56.105:9876");
		producer.start();

		Message msg = new Message("TopicTest", // topic
				"TagA", // tag
				("Hello RocketMQ ").getBytes()// body
		);
		SendResult sendResult = producer.send(msg);
		System.out.println(sendResult);

		producer.shutdown();
	}
}
