package com.anders.ethan.dbproxy.protocol.mysql;

import com.anders.ethan.dbproxy.mysql.MySQLMessage;

/**
 * From Server To Client, at the end of a series of Field Packets, and at the
 * end of a series of Data Packets.With prepared statements, EOF Packet can also
 * end parameter information, which we'll describe later.
 * 
 * <pre>
 * Bytes                 Name
 * -----                 ----
 * 1                     field_count, always = 0xfe
 * 2                     warning_count
 * 2                     Status Flags
 * 
 * @see http://forge.mysql.com/wiki/MySQL_Internals_ClientServer_Protocol#EOF_Packet
 * </pre>
 * 
 * @author mycat
 */
public class EOFPacket extends MySQLPacket {
	public static final byte FIELD_COUNT = (byte) 0xfe;

	public byte fieldCount = FIELD_COUNT;
	public int warningCount;
	public int status = 2;

	public void read(byte[] data) {
		MySQLMessage mm = new MySQLMessage(data);
		packetLength = mm.readUB3();
		packetId = mm.read();
		fieldCount = mm.read();
		warningCount = mm.readUB2();
		status = mm.readUB2();
	}

//	@Override
//	public void write(BufferArray bufferArray) {
//		int size = calcPacketSize();
//		ByteBuffer buffer = bufferArray.checkWriteBuffer(packetHeaderSize
//				+ size);
//		BufferUtil.writeUB3(buffer, size);
//		buffer.put(packetId);
//		buffer.put(fieldCount);
//		BufferUtil.writeUB2(buffer, warningCount);
//		BufferUtil.writeUB2(buffer, status);
//	}

//	public void write(Connection con) {
//		int size = calcPacketSize();
//		ByteBuffer buffer = NetSystem.getInstance().getBufferPool().allocate();
//		BufferUtil.writeUB3(buffer, size);
//		buffer.put(packetId);
//		buffer.put(fieldCount);
//		BufferUtil.writeUB2(buffer, warningCount);
//		BufferUtil.writeUB2(buffer, status);
//		con.write(buffer);
//	}

	@Override
	public int calcPacketSize() {
		return 5;// 1+2+2;
	}

	@Override
	protected String getPacketInfo() {
		return "MySQL EOF Packet";
	}

}