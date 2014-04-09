package com.anders.experiment.布隆过滤器;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;

import junit.framework.Assert;

import org.apache.cassandra.utils.ByteBufferUtil;
import org.apache.cassandra.utils.Murmur3BloomFilter;
import org.apache.cassandra.utils.obs.OpenBitSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;

public class BloomFilterTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGoogleBloomFilter() {
		BloomFilter<BigInteger> bloomFilter = BloomFilter.create(new BigIntegerFunnel(), 1000);
		BigInteger bi1 = new BigInteger("123");
		BigInteger bi2 = new BigInteger("234");
		BigInteger bi3 = new BigInteger("456");
		BigInteger bi4 = new BigInteger("567");
		BigInteger bi5 = new BigInteger("789");
		bloomFilter.put(bi1);
		bloomFilter.put(bi2);
		bloomFilter.put(bi3);
		bloomFilter.put(bi4);
		bloomFilter.put(bi5);
		BigInteger bi = new BigInteger("234");
		boolean isExist = bloomFilter.mightContain(bi);
		Assert.assertTrue(isExist);

		bi = new BigInteger("111");
		isExist = bloomFilter.mightContain(bi);
		Assert.assertFalse(isExist);
	}

	@Test
	public void testCassandraBloomFilter() throws IOException {
		OpenBitSet openBitSet = new OpenBitSet();
		org.apache.cassandra.utils.BloomFilter bloomFilter = new Murmur3BloomFilter(10, openBitSet);
		ByteBuffer bb1 = ByteBufferUtil.bytes("123");
		ByteBuffer bb2 = ByteBufferUtil.bytes("234");
		ByteBuffer bb3 = ByteBufferUtil.bytes("345");
		ByteBuffer bb4 = ByteBufferUtil.bytes("456");
		ByteBuffer bb5 = ByteBufferUtil.bytes("567");
		bloomFilter.add(bb1);
		bloomFilter.add(bb2);
		bloomFilter.add(bb3);
		bloomFilter.add(bb4);
		bloomFilter.add(bb5);

		ByteBuffer bb = ByteBufferUtil.bytes("456");
		Assert.assertTrue(bloomFilter.isPresent(bb));

		bb = ByteBufferUtil.bytes("111");
		Assert.assertFalse(bloomFilter.isPresent(bb));

		bloomFilter.close();
	}
}

class BigIntegerFunnel implements Funnel<BigInteger> {

	private static final long serialVersionUID = -2812839367207057172L;

	@Override
	public void funnel(BigInteger from, PrimitiveSink into) {
		into.putBytes(from.toByteArray());
	}
}
