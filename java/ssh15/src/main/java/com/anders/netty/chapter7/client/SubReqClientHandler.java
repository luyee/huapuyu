package com.anders.netty.chapter7.client;

import com.anders.netty.chapter7.api.RequestVO;
import com.anders.netty.chapter7.api.ResponseVO;

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
			RequestVO requestVO = new RequestVO();
			requestVO.setId(i);
			requestVO.setName("Anders");
			requestVO.setAddress("镇江");
			ctx.writeAndFlush(requestVO);
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ResponseVO responseVO = (ResponseVO) msg;
		if (responseVO.isOk()) {
			System.out.println(String.format("%d %s", responseVO.getId(),
					responseVO.getDesc()));
		}
	}
}