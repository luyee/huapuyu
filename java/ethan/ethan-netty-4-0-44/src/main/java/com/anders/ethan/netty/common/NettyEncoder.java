package com.anders.ethan.netty.common;

import java.nio.ByteBuffer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class NettyEncoder extends MessageToByteEncoder<RemotingCommand> {

	@Override
	protected void encode(ChannelHandlerContext ctx, RemotingCommand msg, ByteBuf out) throws Exception {
		try {
			ByteBuffer header = msg.encodeHeader();
			out.writeBytes(header);
			byte[] body = msg.getBody();
			if (body != null) {
				out.writeBytes(body);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
