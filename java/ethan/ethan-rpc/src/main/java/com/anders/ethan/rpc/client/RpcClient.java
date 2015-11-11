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
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import java.util.concurrent.CountDownLatch;

import com.anders.ethan.rpc.common.RpcDecoder;
import com.anders.ethan.rpc.common.RpcDecoder1;
import com.anders.ethan.rpc.common.RpcEncoder;
import com.anders.ethan.rpc.common.RpcEncoder1;
import com.anders.ethan.rpc.common.RpcRequest;
import com.anders.ethan.rpc.common.RpcResponse;

public class RpcClient extends SimpleChannelInboundHandler<RpcResponse> {
	private String host;
	private int port;
	private RpcResponse response;
	private CountDownLatch countDownLatch = new CountDownLatch(1);

	public RpcClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RpcResponse response)
			throws Exception {

		System.out.println(this + " " + response);

		this.response = response;

		countDownLatch.countDown();
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
					.handler(new ClientInitializer1());

			ChannelFuture future = bootstrap.connect(host, port).sync();
			future.channel().writeAndFlush(request).sync();

			if (response == null) {
				countDownLatch.await();
			}

			future.channel().closeFuture().sync();

			return response;
		} finally {

			group.shutdownGracefully();
		}
	}

	private class ClientInitializer extends ChannelInitializer<SocketChannel> {
		@Override
		public void initChannel(SocketChannel channel) throws Exception {
			channel.pipeline().addLast(new RpcEncoder(RpcRequest.class))
					.addLast(new RpcDecoder(RpcResponse.class))
					.addLast(RpcClient.this);
		}
	}

	private class ClientInitializer1 extends ChannelInitializer<SocketChannel> {
		@Override
		public void initChannel(SocketChannel channel) throws Exception {
			channel.pipeline()
					.addLast(
							new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,
									0, 4, 0, 4))
					.addLast(new RpcDecoder1(RpcResponse.class))
					.addLast(new LengthFieldPrepender(4))
					.addLast(new RpcEncoder1(RpcRequest.class))
					.addLast(RpcClient.this);
		}
	}

}
