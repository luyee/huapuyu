package com.anders.ethan.netty.common;

import java.nio.ByteBuffer;

public class RemotingCommand {
	private byte[] head;
	private byte[] body;

	public static RemotingCommand decode(final ByteBuffer byteBuffer) {
		int length = byteBuffer.limit();
		int headerLength = byteBuffer.getInt();

		byte[] headerData = new byte[headerLength];
		byteBuffer.get(headerData);

		int bodyLength = length - 4 - headerLength;
		byte[] bodyData = null;
		if (bodyLength > 0) {
			bodyData = new byte[bodyLength];
			byteBuffer.get(bodyData);
		}

		RemotingCommand cmd = RemotingSerializable.decode(headerData, RemotingCommand.class);
		cmd.body = bodyData;

		return cmd;
	}

	public ByteBuffer encodeHeader() {
		return encodeHeader(this.body != null ? this.body.length : 0);
	}

	public ByteBuffer encodeHeader(final int bodyLength) {
		// 1> header length size
		int length = 4;

		// 2> header data length
		byte[] headerData = RemotingSerializable.encode(this);
		length += headerData.length;

		// 3> body data length
		length += bodyLength;

		ByteBuffer result = ByteBuffer.allocate(4 + length - bodyLength);

		// length
		result.putInt(length);

		// header length
		result.putInt(headerData.length);

		// header data
		result.put(headerData);

		result.flip();

		return result;
	}

	public byte[] getHead() {
		return head;
	}

	public void setHead(byte[] head) {
		this.head = head;
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}

}
