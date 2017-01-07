package com.anders.ethan.redis.proxy.example1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class RedisReplyEncoder extends MessageToByteEncoder<RedisReply> {

	@Override
	protected void encode(ChannelHandlerContext ctx, RedisReply msg, ByteBuf out) throws Exception {
		System.out.println("RedisReplyEncoder: " + msg);
		msg.write(out);
	}

}
