package com.anders.rabbitmq.vhost;

import java.util.UUID;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {
	private final static String EXCHANGE_NAME = "topic";

	public static void main(String[] argv) throws Exception {
		/**
		 * 创建连接连接到MabbitMQ
		 */
		ConnectionFactory factory = new ConnectionFactory();
		// 设置MabbitMQ所在主机ip或者主机名
		factory.setHost("192.168.56.101");
		factory.setVirtualHost("testvhost");
		// 创建一个连接
		Connection connection = factory.newConnection();
		// 创建一个频道
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "topic");

		String[] routing_keys = new String[] { "kernal.info", "cron.warning",
				"auth.info", "kernel.critical" };

		for (String routing_key : routing_keys) {
			String msg = UUID.randomUUID().toString();
			channel.basicPublish(EXCHANGE_NAME, routing_key, null,
					msg.getBytes());
			System.out.println(" [x] Sent routingKey = " + routing_key
					+ " ,msg = " + msg + ".");
		}

		// 关闭频道和连接
		channel.close();
		connection.close();
	}

}
