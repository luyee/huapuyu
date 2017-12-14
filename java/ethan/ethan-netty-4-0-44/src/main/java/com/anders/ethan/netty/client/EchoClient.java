package com.anders.ethan.netty.client;

import com.anders.ethan.netty.common.NettyDecoder;
import com.anders.ethan.netty.common.NettyEncoder;
import com.anders.ethan.netty.common.RemotingCommand;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public final class EchoClient {

	static final String HOST = System.getProperty("host", "127.0.0.1");
	static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));

	public static void main(String[] args) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup(1);
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.option(ChannelOption.SO_KEEPALIVE, false)
				.option(ChannelOption.SO_SNDBUF, 65535)
				.option(ChannelOption.SO_RCVBUF, 65535)
				.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline p = ch.pipeline();
							p.addLast(new NettyEncoder());
							p.addLast(new NettyDecoder());
//							p.addLast(new LoggingHandler(LogLevel.INFO));
//							p.addLast(new EchoClientHandler());
							p.addLast(new NettyClientHandler());
						}
					});

			ChannelFuture f = b.connect(HOST, PORT).sync();
			
			RemotingCommand remotingCommand = new RemotingCommand();
			remotingCommand.setHead("zhuzhen".getBytes());
			remotingCommand.setBody("hello world".getBytes());
			
			f.channel().writeAndFlush(remotingCommand);

			// Wait until the connection is closed.
			f.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
	}
}
