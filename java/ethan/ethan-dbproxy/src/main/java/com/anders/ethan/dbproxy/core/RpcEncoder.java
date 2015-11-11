package com.anders.ethan.dbproxy.core;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import com.anders.ethan.dbproxy.mysql.Capabilities;
import com.anders.ethan.dbproxy.mysql.SecurityUtil;
import com.anders.ethan.dbproxy.protocol.mysql.AuthPacket;
import com.anders.ethan.dbproxy.protocol.mysql.HandshakePacket;

public class RpcEncoder extends MessageToByteEncoder {

	@Override
	public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out)
			throws Exception {
		if (in instanceof HandshakePacket) {
			HandshakePacket handshakePacket = (HandshakePacket) in;

			AuthPacket authPacket = new AuthPacket();
			authPacket.packetId = 1;
			authPacket.clientFlags = initClientFlags();
			authPacket.maxPacketSize = 16 * 1024 * 1024;
			authPacket.charsetIndex = 33;
			authPacket.user = "root";
			authPacket.password = passwd("123", handshakePacket);
			// authPacket.database = "anders";

			authPacket.write(out);
		}
	}

	private byte[] passwd(String pass, HandshakePacket hs) throws Exception {
		if (pass == null || pass.length() == 0) {
			return null;
		}
		byte[] passwd = pass.getBytes();
		int sl1 = hs.seed.length;
		int sl2 = hs.restOfScrambleBuff.length;
		byte[] seed = new byte[sl1 + sl2];
		System.arraycopy(hs.seed, 0, seed, 0, sl1);
		System.arraycopy(hs.restOfScrambleBuff, 0, seed, sl1, sl2);
		return SecurityUtil.scramble411(passwd, seed);
	}
	
	private long initClientFlags() {
		int flag = 0;

		flag |= Capabilities.CLIENT_LONG_PASSWORD;
		// flag |= Capabilities.CLIENT_FOUND_ROWS;
		flag |= Capabilities.CLIENT_LONG_FLAG;
		// flag |= Capabilities.CLIENT_CONNECT_WITH_DB;
		// flag |= Capabilities.CLIENT_NO_SCHEMA;
		// boolean usingCompress = MycatServer.getInstance().getConfig()
		// .getSystem().getUseCompression() == 1;
		// if (false) {
		// flag |= Capabilities.CLIENT_COMPRESS;
		// }
		// flag |= Capabilities.CLIENT_ODBC;
		flag |= Capabilities.CLIENT_LOCAL_FILES;
		// flag |= Capabilities.CLIENT_IGNORE_SPACE;
		flag |= Capabilities.CLIENT_PROTOCOL_41;
		flag |= Capabilities.CLIENT_INTERACTIVE;
		// flag |= Capabilities.CLIENT_SSL;
		// flag |= Capabilities.CLIENT_IGNORE_SIGPIPE;
		flag |= Capabilities.CLIENT_TRANSACTIONS;
		// flag |= Capabilities.CLIENT_RESERVED;
		flag |= Capabilities.CLIENT_SECURE_CONNECTION;

		// client extension
		flag |= Capabilities.CLIENT_MULTI_STATEMENTS;
		flag |= Capabilities.CLIENT_MULTI_RESULTS;
//		flag |= 65536 * 4;
//		flag |= 65536 * 8;
//		flag |= 65536 * 16;
//		flag |= 65536 * 32;
//		flag |= 65536 * 64;

		return flag;
	}
}
