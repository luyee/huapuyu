package com.anders.ethan.redis.proxy.example1;

import java.io.IOException;

import io.netty.buffer.ByteBuf;

public interface RedisReply<T> {

	byte[] CRLF = new byte[] { '\r', '\n' };

	T data();

	void write(ByteBuf out) throws IOException;

}