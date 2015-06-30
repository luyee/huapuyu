package com.anders.experiment.jvm;

import java.util.Vector;

import net.sourceforge.sizeof.SizeOf;

/**
 * 内存泄露
 * 
 * @author Anders Zhu
 * 
 */
public class MemoryLeak {

	public static void main(String[] args) throws InterruptedException {
		Vector<Object> vector = new Vector<Object>(1000000);
		for (int i = 1; i < 1000000; i++) {
			Object object = new Object();
			vector.add(object);
			object = null;
		}

		System.out.println("first");
		SizeOf.deepSizeOf(vector);

		System.gc();

		System.out.println("second");
		SizeOf.deepSizeOf(vector);

		vector = null;
		System.gc();

		System.out.println("third");
		SizeOf.deepSizeOf(vector);

		Thread.sleep(10000);
	}
}
