package com.anders.ethan.rpc.client;

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

import com.anders.ethan.rpc.common.RpcRequest;
import com.anders.ethan.rpc.common.RpcResponse;

public class RpcClient extends SimpleChannelInboundHandler<RpcResponse> {
	private String host;
	private int port;
	private RpcResponse response;
	private final Object obj = new Object();

	public RpcClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RpcResponse response)
			throws Exception {
		this.response = response;

		synchronized (obj) {
			obj.notifyAll();
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}

	public RpcResponse send(RpcRequest request) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group)
					.channel(NioSocketChannel.class)
					// TODO Anders 是否还要添加其他Options
					// .option(ChannelOption.TCP_NODELAY, true)
					.option(ChannelOption.SO_KEEPALIVE, true)
					.handler(new RpcClientHandler());

			ChannelFuture future = bootstrap.connect(host, port).sync();
			future.channel().writeAndFlush(request).sync();

			synchronized (obj) {
				obj.wait();
			}

			if (response != null) {
				future.channel().closeFuture().sync();
			}
			return response;
		} finally {
			group.shutdownGracefully();
		}
	}

	private class RpcClientHandler extends ChannelInitializer<SocketChannel> {
		@Override
		public void initChannel(SocketChannel channel) throws Exception {
			channel.pipeline()
			// .addLast(new RpcEncoder(RpcRequest.class))
			// .addLast(new RpcDecoder(RpcResponse.class))
					.addLast(RpcClient.this);
		}
	}

}
