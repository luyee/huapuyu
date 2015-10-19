package com.anders.netty.protobuf.client;

import com.anders.netty.protobuf.protos.ResponseProtos;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class SubReqClient {
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
			arg0.pipeline().addLast(new ProtobufVarint32FrameDecoder());
			arg0.pipeline().addLast(new ProtobufDecoder(
					ResponseProtos.Response.getDefaultInstance()));
			arg0.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
			arg0.pipeline().addLast(new ProtobufEncoder());
			arg0.pipeline().addLast(new SubReqClientHandler());
		}
	}

	public static void main(String[] args) throws Exception {
		new SubReqClient().connect(8080, "localhost");
	}
}
