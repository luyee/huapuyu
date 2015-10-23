package com.anders.rabbitmq.tx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Recv {
	// 队列名称
	private final static String QUEUE_NAME = "tx";

	public static void main(String[] argv) throws Exception {
		// 打开连接和创建频道，与发送端一样
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("10.101.137.135");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		// 声明队列，主要为了防止消息接收者先运行此程序，队列还不存在时创建队列。
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);

		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		channel.basicQos(1);

		// 创建队列消费者
		QueueingConsumer consumer = new QueueingConsumer(channel);

		// 指定消费队列
		channel.basicConsume(QUEUE_NAME, false, consumer);

		while (true) {
			// channel.txSelect();
			// nextDelivery是一个阻塞方法（内部实现其实是阻塞队列的take方法）
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			System.out.println(delivery.getProperties().getMessageId());
			String message = new String(delivery.getBody());
			System.out.println(" [x] Received '" + message + "'");
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
			// rollback可以让ack不生效，原理没想清楚
			// channel.txRollback();
			// channel.txCommit();
		}
	}
}
