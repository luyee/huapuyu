package com.anders.experiment.jvm.直接内存;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

/**
 * VM参数：-Xmx20m -XX:MaxDirectMemorySize=10M 如果不指定直接内存大小，默认和堆大小一致
 * 
 * @author Anders
 * 
 */
public class OutOfMemoryError {

	// Exception in thread "main" java.lang.OutOfMemoryError
	// at sun.misc.Unsafe.allocateMemory(Native Method)
	// at com.anders.experiment.jvm.直接内存.OutOfMemoryError.main(OutOfMemoryError.java:20)
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		Field field = Unsafe.class.getDeclaredFields()[0];
		field.setAccessible(true);
		Unsafe unsafe = (Unsafe) field.get(null);
		while (true) {
			unsafe.allocateMemory(1024 * 1024);
		}
	}

}
