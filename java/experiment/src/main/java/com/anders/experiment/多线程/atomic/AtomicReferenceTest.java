package com.anders.experiment.多线程.atomic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

public class AtomicReferenceTest {
	@Test
	public void testAll() throws InterruptedException {
		final AtomicReference value = new AtomicReference(10);
		assertEquals(value.compareAndSet(1, 2), false);
		assertEquals(value.get(), 10);
		assertTrue(value.compareAndSet(10, 3));
		assertEquals(value.get(), 3);
		value.set(0);
		//
		assertEquals(value.getAndSet(5), 0);
		assertEquals(value.get(), 5);
		//
//		final int threadSize = 10;
//		Thread[] ts = new Thread[threadSize];
//		for (int i = 0; i < threadSize; i++) {
//			ts[i] = new Thread() {
//				public void run() {
//					value.incrementAndGet();
//				}
//			};
//		}
//		//
//		for (Thread t : ts) {
//			t.start();
//		}
//		for (Thread t : ts) {
//			t.join();
//		}
//		//
//		assertEquals(value.get(), 5 + threadSize);
	}
}
