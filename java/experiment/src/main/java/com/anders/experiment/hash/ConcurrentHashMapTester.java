package com.anders.experiment.hash;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTester {
	public static void main(String[] args) {
		int concurrencyLevel = 16;
		if (concurrencyLevel > 65536)
			concurrencyLevel = 65536;

		int sshift = 0;
		int ssize = 1;
		while (ssize < concurrencyLevel) {
			++sshift;
			ssize <<= 1;
			System.out.println("sshift:" + sshift);
			System.out.println("ssize:" + ssize);
		}
		int segmentShift = 32 - sshift;
		int segmentMask = ssize - 1;
		// this.segments = Segment.newArray(ssize);

		int initialCapacity = 16;
		if (initialCapacity > 1073741824)
			initialCapacity = 1073741824;
		int c = initialCapacity / ssize;
		if (c * ssize < initialCapacity)
			++c;
		int cap = 1;
		while (cap < c)
			cap <<= 1;

		ConcurrentHashMap<Long, Long> concurrentHashMap = new ConcurrentHashMap<Long, Long>();
		concurrentHashMap.put(10L, 10L);
		concurrentHashMap.put(32768L, 32768L);

		System.out.println(10);
		System.out.println(hash(10));
		System.out.println((hash(10) >>> segmentShift) & segmentMask);
		System.out.println(32768);
		System.out.println(hash(32768));
		System.out.println((hash(32768) >>> segmentShift) & segmentMask);
	}

	private static int hash(int h) {
		h += (h << 15) ^ 0xffffcd7d;
		h ^= (h >>> 10);
		h += (h << 3);
		h ^= (h >>> 6);
		h += (h << 2) + (h << 14);
		return h ^ (h >>> 16);
	}
}
