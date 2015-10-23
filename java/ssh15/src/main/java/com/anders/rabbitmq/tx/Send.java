package com.anders.rabbitmq.tx;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Send {
	// 队列名称
	private final static String QUEUE_NAME = "tx";

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
		// 指定一个队列
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);

		channel.txSelect();
		// 发送的消息
		String message = "提交事务";
		// 往队列中发出一条消息
		channel.basicPublish("", QUEUE_NAME, new BasicProperties("text/plain", null, null, 2, 0, null, null, null,
				"123456789", null, null, null, null, null), message.getBytes());
		System.out.println(" [x] Sent '" + message + "'");
		channel.txCommit();

		channel.txSelect();
		// 发送的消息
		message = "回滚事务";
		// 往队列中发出一条消息
		channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
		System.out.println(" [x] Sent '" + message + "'");
		channel.txRollback();

		// 关闭频道和连接
		channel.close();
		connection.close();
	}
}
