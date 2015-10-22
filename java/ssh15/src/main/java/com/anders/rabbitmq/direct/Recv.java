package com.anders.rabbitmq.direct;

import java.util.Random;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Recv {
	private final static String EXCHANGE_NAME = "direct";
	private static final String[] SEVERITIES = { "info", "warning", "error" };

	public static void main(String[] argv) throws Exception {
		// 打开连接和创建频道，与发送端一样
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("10.101.137.135");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");

		String anonymousName = channel.queueDeclare().getQueue();
		System.out.println(anonymousName);

		String severity = getSeverity();

		channel.queueBind(anonymousName, EXCHANGE_NAME, severity);

		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		// 创建队列消费者
		QueueingConsumer consumer = new QueueingConsumer(channel);
		// 指定消费队列
		channel.basicConsume(anonymousName, true, consumer);
		while (true) {
			// nextDelivery是一个阻塞方法（内部实现其实是阻塞队列的take方法）
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			System.out.println(" [x] Received '" + message + "'");
		}

	}

	private static String getSeverity() {
		Random random = new Random();
		int ranVal = random.nextInt(3);
		return SEVERITIES[ranVal];
	}
}
