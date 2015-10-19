package com.anders.netty.protobuf.client;

import com.anders.netty.protobuf.protos.RequestProtos;
import com.anders.netty.protobuf.protos.ResponseProtos;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class SubReqClientHandler extends ChannelHandlerAdapter {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for (long i = 0; i < 100; i++) {
			RequestProtos.Request.Builder builder = RequestProtos.Request
					.newBuilder();
			builder.setId(i);
			builder.setName("Anders");
			builder.setAddress("镇江");
			ctx.write(builder.build());
		}
		ctx.flush();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ResponseProtos.Response response = (ResponseProtos.Response) msg;
		if (response.getOk()) {
			System.out.println(String.format("%d %s", response.getId(),
					response.getDesc()));
		}
	}
}