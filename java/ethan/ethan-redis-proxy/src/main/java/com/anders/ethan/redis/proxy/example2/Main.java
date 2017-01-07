package com.anders.ethan.redis.proxy.example2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;

public class Main {
	private static Integer port = 6379;

	public static void main(String[] args) throws InterruptedException {
		final RedisCommandHandler commandHandler = new RedisCommandHandler(
				new SimpleRedisServer());

		ServerBootstrap b = new ServerBootstrap();
		final DefaultEventExecutorGroup group = new DefaultEventExecutorGroup(1);
		try {
			b.group(new NioEventLoopGroup(), new NioEventLoopGroup())
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 100).localAddress(port)
					.childOption(ChannelOption.TCP_NODELAY, true)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch)
								throws Exception {
							ChannelPipeline p = ch.pipeline();
							p.addLast(new RedisCommandDecoder());
							p.addLast(new RedisReplyEncoder());
							p.addLast(group, commandHandler);
						}
					});

			ChannelFuture f = b.bind().sync();
			f.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
	}
}
