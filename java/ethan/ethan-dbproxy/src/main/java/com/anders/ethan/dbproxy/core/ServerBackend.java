package com.anders.ethan.dbproxy.core;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;


public class ServerBackend {
public static void main(String[] args) throws InterruptedException {
	EventLoopGroup group = new NioEventLoopGroup();
	try {
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(group)
				.channel(NioSocketChannel.class)
				// TODO Anders 是否还要添加其他Options
				// .option(ChannelOption.TCP_NODELAY, true)
				.option(ChannelOption.SO_KEEPALIVE, true)
				.handler(new ClientInitializer());

		ChannelFuture future = bootstrap.connect("127.0.0.1", 3307).sync();

		future.channel().closeFuture().sync();

	} finally {

		group.shutdownGracefully();
	}
	
	
}
}

class ClientInitializer extends ChannelInitializer<SocketChannel> {
	@Override
	public void initChannel(SocketChannel channel) throws Exception {
		channel.pipeline()
//				.addLast(
//						new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,
//								0, 3, 0, 3))
				.addLast(new RpcDecoder())
//				.addLast(new LengthFieldPrepender(3))
				.addLast(new RpcEncoder())
				.addLast(new RpcClient());
	}
}
