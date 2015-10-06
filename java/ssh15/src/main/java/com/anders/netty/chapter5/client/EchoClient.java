package com.anders.netty.chapter5.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class EchoClient {
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
			ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
			arg0.pipeline()
					.addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
			arg0.pipeline().addLast(new StringDecoder());
			arg0.pipeline().addLast(new EchoClientHandler());
		}
	}

	public static void main(String[] args) throws Exception {
		// 13 CR 回车
		// 10 LF 换行
		// byte[] bytes = System.getProperty("line.separator").getBytes();
		// for (byte b : bytes) {
		// System.out.println(b);
		// }

		new EchoClient().connect(8080, "127.0.0.1");
	}
}
