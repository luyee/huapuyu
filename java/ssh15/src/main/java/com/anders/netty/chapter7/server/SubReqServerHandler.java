package com.anders.netty.chapter7.server;

import com.anders.netty.chapter7.api.RequestVO;
import com.anders.netty.chapter7.api.ResponseVO;

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
		RequestVO requestVO = (RequestVO) msg;
		System.out.println(String.format("%d %s %s", requestVO.getId(),
				requestVO.getName(), requestVO.getAddress()));

		ResponseVO responseVO = new ResponseVO();
		responseVO.setId(requestVO.getId());
		responseVO.setOk(true);
		responseVO.setDesc("请求成功");

		ctx.writeAndFlush(responseVO);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx)
			throws Exception {
		ctx.flush();
	}
}
