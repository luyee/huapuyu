package com.anders.ethan.rpc.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class RpcEncoder extends MessageToByteEncoder {

	private Class<?> genericClass;

	public RpcEncoder(Class<?> genericClass) {
		this.genericClass = genericClass;
	}

	@Override
	public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out)
			throws Exception {
		if (genericClass.isInstance(in)) {
			byte[] data = SerializationUtils.serialize(in);
			// 先写四字节长度
			out.writeInt(data.length);
			out.writeBytes(data);
		}
	}
}
