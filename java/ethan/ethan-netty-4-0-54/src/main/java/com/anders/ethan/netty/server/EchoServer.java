package com.anders.ethan.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public final class EchoServer {

//	static final boolean SSL = System.getProperty("ssl") != null;
	static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));

	public static void main(String[] args) throws Exception {
		// Configure SSL.
		// final SslContext sslCtx;
		// if (SSL) {
		// SelfSignedCertificate ssc = new SelfSignedCertificate();
		// sslCtx = SslContextBuilder.forServer(ssc.certificate(),
		// ssc.privateKey()).build();
		// } else {
		// sslCtx = null;
		// }

		// Configure the server.
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1024)
					// .option(ChannelOption.SO_REUSEADDR, true)
					// .option(ChannelOption.SO_KEEPALIVE, false)
					// .childOption(ChannelOption.TCP_NODELAY, true)
					// .option(ChannelOption.SO_SNDBUF, 65535)
					// .option(ChannelOption.SO_RCVBUF, 65535)
					.handler(new LoggingHandler(LogLevel.INFO)).childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline p = ch.pipeline();
							// if (sslCtx != null) {
							// p.addLast(sslCtx.newHandler(ch.alloc()));
							// }
							// p.addLast(new LoggingHandler(LogLevel.INFO));
							p.addLast(new EchoServerHandler());
						}
					});

			// Start the server.
			ChannelFuture f = b.bind(PORT).sync();

			// Wait until the server socket is closed.
			f.channel().closeFuture().sync();
		} finally {
			// Shut down all event loops to terminate all threads.
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
