package com.anders.ethan.rpc.common;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

public final class SerializationUtils {

	private static Map<Class<?>, Schema<?>> cachedSchemaMap = new ConcurrentHashMap<Class<?>, Schema<?>>();
	private static Objenesis objenesis = new ObjenesisStd(true);

	private SerializationUtils() {
	}

	@SuppressWarnings("unchecked")
	private static <T> Schema<T> getSchema(Class<T> clazz) {
		Schema<T> schema = (Schema<T>) cachedSchemaMap.get(clazz);
		if (schema == null) {
			schema = RuntimeSchema.createFrom(clazz);
			if (schema != null) {
				cachedSchemaMap.put(clazz, schema);
			}
		}
		return schema;
	}

	@SuppressWarnings("unchecked")
	public static <T> byte[] serialize(T obj) {
		Class<T> clazz = (Class<T>) obj.getClass();
		LinkedBuffer buffer = LinkedBuffer
				.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
		try {
			Schema<T> schema = getSchema(clazz);
			return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			buffer.clear();
		}
	}

	public static <T> T deserialize(byte[] data, Class<T> clazz) {
		try {
			T message = (T) objenesis.newInstance(clazz);
			Schema<T> schema = getSchema(clazz);
			ProtostuffIOUtil.mergeFrom(data, message, schema);
			return message;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
