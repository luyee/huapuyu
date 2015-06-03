package com.anders.experiment.hash;

import java.util.concurrent.ConcurrentHashMap;

/**
 * ConcurrentHashMap分成多个segment（默认16），每一个segment有点类似一个HashMap，put加锁；get不加锁（
 * 通过volatile实现），除非获取到的对象为null，则进行加锁的get（原因看下面） /** Reads value field of an
 * entry under lock. Called if value field ever appears to be null. This is
 * possible only if a compiler happens to reorder a HashEntry initialization
 * with its table assignment, which is legal under memory model but is not known
 * to ever occur.
 */
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
		concurrentHashMap.get(1L);
		concurrentHashMap.put(10L, 10L);
		concurrentHashMap.put(32768L, 32768L);
		concurrentHashMap.size();
		concurrentHashMap.isEmpty();
		for (long i = 0; i < 100000; i++) {
			concurrentHashMap.put(i, i);
			if (i == 99999) {
				System.out.println("ok");
			}
		}

		// segment是固定大小的，ConcurrentHashMap创建时确定了size就没法扩容了，比如上面ConcurrentHashMap的segments为16，本处ConcurrentHashMap的segments为128
		ConcurrentHashMap<Long, Long> concurrentHashMap1 = new ConcurrentHashMap<Long, Long>(
				100, 0.75f, 100);
		concurrentHashMap1.get(1L);
		concurrentHashMap1.put(10L, 10L);
		concurrentHashMap1.put(32768L, 32768L);
		for (long i = 0; i < 100000; i++) {
			concurrentHashMap1.put(i, i);
			if (i == 99999) {
				System.out.println("ok");
			}
		}

		System.out.println(10);
		System.out.println(hash(10));
		System.out.println((hash(10) >>> segmentShift) & segmentMask);
		System.out.println(32768);
		System.out.println(hash(32768));
		System.out.println((hash(32768) >>> segmentShift) & segmentMask);

		System.out.println(Integer.parseInt("0001111", 2) & 15);
		System.out.println(Integer.parseInt("0011111", 2) & 15);
		System.out.println(Integer.parseInt("0111111", 2) & 15);
		System.out.println(Integer.parseInt("1111111", 2) & 15);

		concurrentHashMap.put(Long.parseLong("0001111", 2), 1L);
		concurrentHashMap.put(Long.parseLong("0011111", 2), 1L);

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