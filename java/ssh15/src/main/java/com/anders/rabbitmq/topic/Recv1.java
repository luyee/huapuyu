package com.anders.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Recv1 {
	private final static String EXCHANGE_NAME = "topic";

	public static void main(String[] argv) throws Exception {
		// 打开连接和创建频道，与发送端一样
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.56.101");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");

		String anonymousName = channel.queueDeclare().getQueue();
		System.out.println(anonymousName);

		// 接收所有与kernel相关的消息
		channel.queueBind(anonymousName, EXCHANGE_NAME, "auth.*");

		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		// 创建队列消费者
		QueueingConsumer consumer = new QueueingConsumer(channel);
		// 指定消费队列
		channel.basicConsume(anonymousName, true, consumer);

		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			String routingKey = delivery.getEnvelope().getRoutingKey();

			System.out.println(" [x] Received routingKey = " + routingKey
					+ ",msg = " + message + ".");
		}

	}

}
