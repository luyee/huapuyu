package com.anders.ethan.dbproxy.core;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import com.anders.ethan.dbproxy.mysql.ByteUtil;
import com.anders.ethan.dbproxy.mysql.SeqUtils;
import com.anders.ethan.dbproxy.protocol.mysql.CommandPacket;
import com.anders.ethan.dbproxy.protocol.mysql.EOFPacket;
import com.anders.ethan.dbproxy.protocol.mysql.FieldPacket;
import com.anders.ethan.dbproxy.protocol.mysql.HandshakePacket;
import com.anders.ethan.dbproxy.protocol.mysql.OkPacket;
import com.anders.ethan.dbproxy.protocol.mysql.ResultSetHeaderPacket;
import com.anders.ethan.dbproxy.protocol.mysql.RowDataPacket;

public class RpcDecoder extends ByteToMessageDecoder {

	@Override
	public final void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {

		int length = in.readableBytes();
		System.out.println(this + "recevie : " + length);

		if (length <= 0) {
			return;
		}

		final byte[] data = new byte[length];
		in.readBytes(data);
		byte seq = data[3];

		if (SeqUtils.getGlobalSeq() == 0) {
			if (seq == 0) {
				HandshakePacket handshakePacket = new HandshakePacket();
				handshakePacket.read(data);
				out.add(handshakePacket);
			} else if (seq == 2) {
				OkPacket okPacket = new OkPacket();
				okPacket.read(data);
				out.add(okPacket);
			}
		} else if (SeqUtils.getGlobalSeq() == 1) {
			OkPacket okPacket = new OkPacket();
			okPacket.read(data);
			out.add(okPacket);
		} else if (SeqUtils.getGlobalSeq() == 2) {
				// header
				ResultSetHeaderPacket resultSetHeaderPacket = new ResultSetHeaderPacket();
				resultSetHeaderPacket.read(data);
				
				int pos = resultSetHeaderPacket.packetLength + 4;
				byte[] temp = new byte[length - pos];
				System.arraycopy(data, pos, temp, 0, temp.length);
				
				// field 1
				FieldPacket fieldPacket = new FieldPacket();
				fieldPacket.read(temp);
				
				pos += fieldPacket.packetLength + 4;
				temp = new byte[length - pos];
				System.arraycopy(data, pos, temp, 0, temp.length);
				
				// field 2
				fieldPacket = new FieldPacket();
				fieldPacket.read(temp);
				
				pos += fieldPacket.packetLength + 4;
				temp = new byte[length - pos];
				System.arraycopy(data, pos, temp, 0, temp.length);

				// field eof
				EOFPacket eofPacket = new EOFPacket();
				eofPacket.read(temp);

				pos += eofPacket.packetLength + 4;
				temp = new byte[length - pos];
				System.arraycopy(data, pos, temp, 0, temp.length);
				
				// row data
				RowDataPacket rowDataPacket = new RowDataPacket(2);
				rowDataPacket.read(temp);
				
				pos += rowDataPacket.packetLength + 4;
				temp = new byte[length - pos];
				System.arraycopy(data, pos, temp, 0, temp.length);
				
				// eof
				eofPacket = new EOFPacket();
				eofPacket.read(temp);
				
				System.out.println(temp.length == (eofPacket.packetLength + 4));
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
