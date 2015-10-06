package com.anders.netty.chapter4.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeServerHandler extends ChannelHandlerAdapter {

	private int counter;

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// ByteBuf buf = (ByteBuf) msg;
		// byte[] request = new byte[buf.readableBytes()];
		// buf.readBytes(request);
		// String body = new String(request, "UTF-8");
		// String body = new String(request, "UTF-8").substring(0,
		// request.length - System.getProperty("line.separator").length());
		String body = (String) msg;
		System.out.println(String.format(
				"The time server receive order : %s; counter : %d", body,
				++counter));
		String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body)
				? new java.util.Date(System.currentTimeMillis()).toString()
				: "BAD ORDER";
		currentTime += System.getProperty("line.separator");
		ByteBuf response = Unpooled.copiedBuffer(currentTime.getBytes());
		// ctx.write(response);
		ctx.writeAndFlush(response);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx)
			throws Exception {
		ctx.flush();
	}
}
