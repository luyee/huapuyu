package com.anders.netty.chapter4.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class TimeClient {
	public void connect(int port, String host) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group).channel(NioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChildChannelHandler());

			ChannelFuture cf = bootstrap.connect(host, port).sync();
			cf.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
	}

	private class ChildChannelHandler
			extends ChannelInitializer<SocketChannel> {
		@Override
		protected void initChannel(SocketChannel arg0) throws Exception {
			arg0.pipeline().addLast(new LineBasedFrameDecoder(1024));
			arg0.pipeline().addLast(new StringDecoder());
			arg0.pipeline().addLast(new TimeClientHandler());
		}
	}

	public static void main(String[] args) throws Exception {
		// 13 CR 回车
		// 10 LF 换行
		// byte[] bytes = System.getProperty("line.separator").getBytes();
		// for (byte b : bytes) {
		// System.out.println(b);
		// }

		new TimeClient().connect(8080, "127.0.0.1");
	}
}
