package com.anders.ethan.dbproxy.core;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class RpcDecoder extends ByteToMessageDecoder {


	@Override
	public final void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {

		
		byte[] data = new byte[3];
		data[0] = in.readByte();
		data[1] = in.readByte();
		data[2] = in.readByte();
		
		System.out.println(in.readableBytes());
		
		int position = 0;
		  int i = data[position++] & 0xff;
	        i |= (data[position++] & 0xff) << 8;
	        i |= (data[position++] & 0xff) << 16;
	        
	        System.out.println(i);
		
		if (in.readableBytes() < 4) {
			return;
		}
		in.markReaderIndex();
		int dataLength = in.readInt();
		if (dataLength < 0) {
			// TODO Anders 是不是应该抛出异常
			ctx.close();
		}
		// 会不会出现拆包和粘包问题
		if (in.readableBytes() < dataLength) {
			in.resetReaderIndex();
			return;
		}
//		byte[] data = new byte[dataLength];
//		in.readBytes(data);

//		Object obj = SerializationUtils.deserialize(data, genericClass);
//		out.add(obj);
	}
}
