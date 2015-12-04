package com.anders.rabbitmq.rpc;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.rabbitmq.client.ShutdownSignalException;

public class RabbitRpcClient {
	public static final String RPC_QUEUE_NAME = "rpc_queue";

	public static void main(String[] args) throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException, TimeoutException {

		ConnectionFactory connFac = new ConnectionFactory();
		connFac.setUsername("lusong");
		connFac.setPassword("lusong");
		connFac.setHost("10.199.211.41");
		Connection conn = connFac.newConnection();
		Channel channel = conn.createChannel();

		// 响应QueueName ，服务端将会把要返回的信息发送到该Queue
		String responseQueue = channel.queueDeclare().getQueue();
		System.out.println("客户端发送消息replyTo:" + responseQueue);

		String correlationId = UUID.randomUUID().toString();
		BasicProperties props = new BasicProperties.Builder().replyTo(responseQueue).correlationId(correlationId).build();

		String message = "owen.lu";
		channel.basicPublish("", RPC_QUEUE_NAME, props, message.getBytes("UTF-8"));
		System.out.println("客户端发送消息..");

		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(responseQueue, consumer);
		System.out.println("客户端订阅服务端消息responseQueue:" + responseQueue);

		while (true) {
			Delivery delivery = consumer.nextDelivery();
			System.out.println("客户端收到delivery>>>>>>>>:" + ToStringBuilder.reflectionToString(delivery));

			if (delivery.getProperties().getCorrelationId().equals(correlationId)) {
				String result = new String(delivery.getBody());
				System.out.println("收到服务端回复:" + result + "\n");
			}

			correlationId = UUID.randomUUID().toString();
			props = new BasicProperties.Builder().replyTo(responseQueue).correlationId(correlationId).build();
			message = "owen.lu" + RandomUtils.nextInt(100000);
			channel.basicPublish("", RPC_QUEUE_NAME, props, message.getBytes("UTF-8"));
			System.out.println("客户端发送消息>>>>>>>>:" + message);

			Thread.sleep(1000);
		}
	}
}
