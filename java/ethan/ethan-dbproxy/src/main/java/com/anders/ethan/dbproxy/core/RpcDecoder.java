package com.anders.ethan.dbproxy.core;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import com.anders.ethan.dbproxy.protocol.mysql.HandshakePacket;

public class RpcDecoder extends ByteToMessageDecoder {

	@Override
	public final void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {

		int length = in.readableBytes();

		if (length <= 0) {
			return;
		}

		final byte[] data = new byte[length];
		in.readBytes(data);

		if (data[3] == 0) {
			HandshakePacket handshakePacket = new HandshakePacket();
			handshakePacket.read(data);
			out.add(handshakePacket);
		}

		// in.markReaderIndex();
		// System.out.println(in.readMedium());
		// in.resetReaderIndex();

		// byte[] data = new byte[3];
		// data[0] = in.readByte();
		// data[1] = in.readByte();
		// data[2] = in.readByte();
		//
		// System.out.println(in.readByte());
		// System.out.println(in.readByte());
		//
		// System.out.println(in.readableBytes());
		//
		// int position = 0;
		// int i = data[position++] & 0xff;
		// i |= (data[position++] & 0xff) << 8;
		// i |= (data[position++] & 0xff) << 16;
		//
		// System.out.println(i);

	}
}
