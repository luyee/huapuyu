package com.anders.experiment.jvm.方法区;

import java.util.ArrayList;
import java.util.List;

/**
 * VM参数：-XX:PermSize=10M -XX:MaxPermSize=10M
 * 
 * @author Anders
 * 
 */
public class OutOfMemoryError {

	// 210504
	// Exception in thread "main" java.lang.OutOfMemoryError: PermGen space
	// at java.lang.String.intern(Native Method)
	// at com.anders.experiment.jvm.方法区.OutOfMemoryError.main(OutOfMemoryError.java:22)
	public static void main(String[] args) throws Throwable {
		List<String> list = new ArrayList<String>();
		int i = 0;
		try {
			while (true) {
				list.add(String.valueOf(i++).intern());
			}
		}
		catch (Throwable e) {
			System.out.println(i);
			throw e;
		}
	}

}
