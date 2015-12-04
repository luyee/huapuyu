package com.anders.rabbitmq.rpc;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.rabbitmq.client.ShutdownSignalException;

public class RabbitRpcServer {

	public static final String RPC_QUEUE_NAME = "rpc_queue";

	public static String sayHello(String name) {
		return "hello " + name;
	}

	/**
	 * 先启动服务端
	 * 
	 * @param args
	 * @throws IOException
	 * @throws ShutdownSignalException
	 * @throws ConsumerCancelledException
	 * @throws TimeoutException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, ShutdownSignalException, ConsumerCancelledException, TimeoutException, InterruptedException {

		ConnectionFactory connFac = new ConnectionFactory();
		connFac.setUsername("lusong");
		connFac.setPassword("lusong");
		connFac.setHost("10.199.211.41");

		Connection conn = connFac.newConnection();
		Channel channel = conn.createChannel();
		channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);

		QueueingConsumer consumer = new QueueingConsumer(channel);

		channel.basicConsume(RPC_QUEUE_NAME, false, consumer);

		while (true) {
			System.out.println("服务端等待接收消息..");
			Delivery deliver = consumer.nextDelivery();
			System.out.println("服务端成功收到消息..");

			System.out.println(">>>>>>>>>>>>>>>>>>>服务端等待接收消息的deliver.getEnvelope():" + ToStringBuilder.reflectionToString(deliver.getEnvelope()));

			BasicProperties props = deliver.getProperties();
			System.out.println(">>>>>>>>>>>>>>>>>>>服务端等待接收消息的props:" + ToStringBuilder.reflectionToString(props));

			String message = new String(deliver.getBody(), "UTF-8");
			String responseMessage = sayHello(message);
			BasicProperties responseProps = new BasicProperties.Builder().correlationId(props.getCorrelationId()).build();

			// 将结果返回到客户端Queue
			channel.basicPublish("", props.getReplyTo(), responseProps, responseMessage.getBytes("UTF-8"));

			// 向客户端确认消息
			channel.basicAck(deliver.getEnvelope().getDeliveryTag(), false);
			System.out.println("服务端返回消息完成..");
		}

	}
}
