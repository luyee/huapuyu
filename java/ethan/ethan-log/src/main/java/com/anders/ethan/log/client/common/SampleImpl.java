package com.anders.ethan.log.client.common;

import java.util.concurrent.atomic.AtomicLong;

public class SampleImpl implements Sample {
	private AtomicLong count = new AtomicLong();
	private int baseNumber = 100;
	private Long lastTime = -1L;

	public SampleImpl() {
		lastTime = System.currentTimeMillis();
	}

	public boolean isSample() {
		boolean isSample = true;
		long n = count.incrementAndGet();
		if (System.currentTimeMillis() - lastTime < 1000) {
			if (n > baseNumber) {
				n = n % 10;
				if (n != 0) {
					isSample = false;
				}
			}
		} else {
			count.getAndSet(0);
			lastTime = System.currentTimeMillis();//
		}
		return isSample;
	}
}
