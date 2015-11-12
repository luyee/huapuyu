package com.anders.ethan.dbproxy.mysql;

public class SeqUtils {
	private static ThreadLocal<Byte> GLOBAL_SEQ = new ThreadLocal<Byte>();

	static {
		GLOBAL_SEQ.set((byte) 0);
	}

	public static byte getGlobalSeq() {
		return GLOBAL_SEQ.get();
	}

	public static void setGlobalSeq(byte seq) {
		GLOBAL_SEQ.set(seq);
	}
}
