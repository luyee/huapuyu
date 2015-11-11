package com.anders.ethan.dbproxy.core;

import com.anders.ethan.dbproxy.protocol.mysql.HandshakePacket;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ServerBackend extends SimpleChannelInboundHandler<Object> {
	public void connect() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group)
					.channel(NioSocketChannel.class)
					// TODO Anders 是否还要添加其他Options
					// .option(ChannelOption.TCP_NODELAY, true)
					.option(ChannelOption.SO_KEEPALIVE, true)
					.handler(new ClientInitializer());

			ChannelFuture future = bootstrap.connect("192.168.2.71", 3306)
					.sync();

			future.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}

	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		System.out.println(msg);

		// if (msg instanceof HandshakePacket) {
		ctx.writeAndFlush(msg).sync();
		// }

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}

	class ClientInitializer extends ChannelInitializer<SocketChannel> {
		@Override
		public void initChannel(SocketChannel channel) throws Exception {
			channel.pipeline()
			// .addLast(
			// new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,
			// 0, 3, 0, 3))
					.addLast(new RpcDecoder())
					// .addLast(new LengthFieldPrepender(3))
					.addLast(new RpcEncoder()).addLast(ServerBackend.this);
		}
	}
}
