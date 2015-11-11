package com.anders.ethan.rpc.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class RpcDecoder1 extends MessageToMessageDecoder<ByteBuf> {

	private Class<?> genericClass;

	public RpcDecoder1(Class<?> genericClass) {
		this.genericClass = genericClass;
	}

	@Override
	public final void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		final int length = in.readableBytes();
		final byte[] data = new byte[length];
		// in.readBytes(data);
		in.getBytes(in.readerIndex(), data, 0, length);

		Object obj = SerializationUtils.deserialize(data, genericClass);
		out.add(obj);
	}
}
