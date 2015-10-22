package com.anders.rabbitmq.direct;

import java.util.Random;
import java.util.UUID;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {
	private final static String EXCHANGE_NAME = "direct";
	private static final String[] SEVERITIES = { "info", "warning", "error" };

	public static void main(String[] argv) throws Exception {
		/**
		 * 创建连接连接到MabbitMQ
		 */
		ConnectionFactory factory = new ConnectionFactory();
		// 设置MabbitMQ所在主机ip或者主机名
		factory.setHost("10.101.137.135");
		// 创建一个连接
		Connection connection = factory.newConnection();
		// 创建一个频道
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "direct");

		// 发送6条消息
		for (int i = 0; i < 6; i++) {
			String severity = getSeverity();
			String message = severity + "_log :" + UUID.randomUUID().toString();
			// 发布消息至转发器，指定routingkey
			channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");
		}

		// 关闭频道和连接
		channel.close();
		connection.close();
	}

	private static String getSeverity() {
		Random random = new Random();
		int ranVal = random.nextInt(3);
		return SEVERITIES[ranVal];
	}
}
