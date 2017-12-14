package com.anders.ethan.netty.client;

import com.anders.ethan.netty.common.RemotingCommand;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public  class NettyClientHandler extends SimpleChannelInboundHandler<RemotingCommand> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RemotingCommand msg) throws Exception {
    	System.out.println(new String(msg.getHead() + ":" + new String(msg.getBody())));
    }
}
