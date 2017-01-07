package com.anders.ethan.redis.proxy.example2;

import com.anders.ethan.redis.proxy.example1.BulkReply;

public interface RedisServer {

	public BulkReply get(byte[] key0) throws RedisException;

	public StatusReply set(byte[] key0, byte[] value1) throws RedisException;

}
