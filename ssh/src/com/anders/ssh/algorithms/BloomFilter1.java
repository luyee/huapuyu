package com.anders.ssh.algorithms;

import java.util.BitSet;

/**
 * <p>
 * 布隆算法 BloomFilter <i>http://www.googlechinablog.com/2007/07/bloom-filter.html</i>
 * </p>
 * <strong>扩展<tt>hash</tt>算法</strong>
 * <p>
 * 基本思想：在内存里面开辟一个区域，所有位置为零，然后 用不同的hash算法对要存入的数据计算hash值，每个hash 值对内存位数求模，得到的数在内存位置上值1，置位之前需要现 判断是否已经置位，对于插入的值，所有的位置都是1时， 才认为 是重复
 * </p>
 * 
 */
public class BloomFilter1 {
	private static int defaultSize = 2 << 24;// 2的24次方。

	private static int basic = defaultSize - 1;

	private static BitSet bits;// 内存置位

	static int check(int h) {
		return basic & h;
	}

	static int hash(int h) {
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}

	public BloomFilter1() {
		bits = new BitSet(defaultSize);
	}

	public boolean contains(String url) {
		if (url == null) {
			return true;
		}
		int pos1 = check(hash(url.hashCode() + 2));
		int pos2 = check(hash(url.hashCode() + 4));
		int pos3 = check(hash(url.hashCode() + 6));
		if (bits.get(pos1) && bits.get(pos2) && bits.get(pos3)) {
			return true;
		}
		return false;
	}

	public void add(String url) {
		if (url == null) {
			return;
		}
		int pos1 = check(hash(url.hashCode() + 2));
		int pos2 = check(hash(url.hashCode() + 4));
		int pos3 = check(hash(url.hashCode() + 6));
		bits.set(pos1);
		bits.set(pos2);
		bits.set(pos3);
	}

	public static void main(String[] args) {
		String ss = "ssss";
		BloomFilter1 bf = new BloomFilter1();
		bf.add(ss);
		System.out.println(bf.contains(ss));
	}
}
