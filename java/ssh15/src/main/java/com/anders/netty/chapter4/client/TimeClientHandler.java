package com.anders.netty.chapter4.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeClientHandler extends ChannelHandlerAdapter {

	// private final ByteBuf firstMessage;
	private int counter;
	private byte[] request;

	public TimeClientHandler() {
		// byte[] request = "QUERY TIME ORDER".getBytes();
		// firstMessage = Unpooled.buffer(request.length);
		// firstMessage.writeBytes(request);
		request = ("QUERY TIME ORDER" + System.getProperty("line.separator"))
				.getBytes();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// ctx.writeAndFlush(firstMessage);
		ByteBuf message = null;
		for (int i = 0; i < 100; i++) {
			message = Unpooled.buffer(request.length);
			message.writeBytes(request);
			ctx.writeAndFlush(message);
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// ByteBuf buf = (ByteBuf) msg;
		// byte[] request = new byte[buf.readableBytes()];
		// buf.readBytes(request);
		// String body = new String(request, "UTF-8");
		String body = (String) msg;
		System.out.println(
				String.format("Now is : %s; counter : %d", body, ++counter));
	}
}