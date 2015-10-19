package com.anders.netty.protobuf.server;

import com.anders.netty.protobuf.protos.RequestProtos;
import com.anders.netty.protobuf.protos.ResponseProtos;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class SubReqServerHandler extends ChannelHandlerAdapter {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		RequestProtos.Request request = (RequestProtos.Request) msg;
		System.out.println(String.format("%d %s %s", request.getId(),
				request.getName(), request.getAddress()));

		ResponseProtos.Response.Builder builder = ResponseProtos.Response
				.newBuilder();
		builder.setId(request.getId());
		builder.setOk(true);
		builder.setDesc("请求成功");
		ctx.writeAndFlush(builder.build());
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx)
			throws Exception {
		ctx.flush();
	}
}
