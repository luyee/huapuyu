package com.anders.ethan.redis.proxy.example1;

import java.io.IOException;

import io.netty.buffer.ByteBuf;

public class IntegerReply implements RedisReply<Integer> {

	private static final char MARKER = ':';

	private final int data;

	public IntegerReply(int data) {
		this.data = data;
	}

	@Override
	public Integer data() {
		return this.data;
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeByte(MARKER);
		out.writeBytes(String.valueOf(data).getBytes());
		out.writeBytes(CRLF);
	}

	@Override
	public String toString() {
		return "IntegerReply{" + "data=" + data + '}';
	}

}
