package com.anders.ethan.rpc.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.anders.ethan.rpc.common.RpcDecoder;
import com.anders.ethan.rpc.common.RpcDecoder1;
import com.anders.ethan.rpc.common.RpcEncoder;
import com.anders.ethan.rpc.common.RpcEncoder1;
import com.anders.ethan.rpc.common.RpcRequest;
import com.anders.ethan.rpc.common.RpcResponse;
import com.anders.ethan.rpc.common.RpcService;
import com.anders.ethan.rpc.zk.ServiceRegistry;

public class RpcServer implements ApplicationContextAware, InitializingBean {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(RpcServer.class);

	private String serverAddress;
	private ServiceRegistry serviceRegistry;
	private Map<String, Object> providersMap = new HashMap<String, Object>();

	public RpcServer(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	public RpcServer(String serverAddress, ServiceRegistry serviceRegistry) {
		this.serverAddress = serverAddress;
		this.serviceRegistry = serviceRegistry;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 128)
					.childHandler(new ServerInitializer1())
					.childOption(ChannelOption.SO_KEEPALIVE, true);

			String[] array = serverAddress.split(":");
			String host = array[0];
			int port = Integer.parseInt(array[1]);

			ChannelFuture future = bootstrap.bind(host, port).sync();
			LOGGER.debug("server started on port {}", port);

			// if (serviceRegistry != null) {
			// serviceRegistry.register(serverAddress); // 注册服务地址
			// }

			future.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		Map<String, Object> serviceBeansMap = applicationContext
				.getBeansWithAnnotation(RpcService.class);

		if (MapUtils.isNotEmpty(serviceBeansMap)) {
			for (Object serviceBean : serviceBeansMap.values()) {
				String interfaceName = serviceBean.getClass()
						.getAnnotation(RpcService.class).value().getName();
				providersMap.put(interfaceName, serviceBean);
			}
		}
	}

	private class ServerInitializer extends ChannelInitializer<SocketChannel> {
		@Override
		public void initChannel(SocketChannel channel) throws Exception {
			channel.pipeline()
					// .addLast(new ProtobufVarint32FrameDecoder())
					.addLast(new RpcDecoder(RpcRequest.class))
					// .addLast(new ProtobufVarint32LengthFieldPrepender())
					.addLast(new RpcEncoder(RpcResponse.class))
					.addLast(new RpcServerHandler(providersMap));
		}
	}

	private class ServerInitializer1 extends ChannelInitializer<SocketChannel> {
		@Override
		public void initChannel(SocketChannel channel) throws Exception {
			channel.pipeline()
					.addLast(
							new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,
									0, 4, 0, 4))
					.addLast(new RpcDecoder1(RpcRequest.class))
					.addLast(new LengthFieldPrepender(4))
					.addLast(new RpcEncoder1(RpcResponse.class))
					.addLast(new RpcServerHandler(providersMap));
		}
	}

}
