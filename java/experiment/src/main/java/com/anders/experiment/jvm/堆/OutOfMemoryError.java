package com.anders.experiment.jvm.堆;

import java.util.ArrayList;
import java.util.List;

/**
 * VM参数：-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * 限制Java堆的大小为20MB，不可扩展（将堆的最小值-Xms和最大值-Xmx设置为一样，即可避免堆自动扩展）
 * 同时通过-XX:+HeapDumpOnOutOfMemoryError可以让虚拟机在出现内存溢出异常时Dump出当前的内存堆转储快照
 * 
 * @author Anders Zhu
 * 
 */
public class OutOfMemoryError {

	public static void main(String[] args) {
		List<OOMObject> list = new ArrayList<OOMObject>();

		// Exception in thread "main" java.lang.OutOfMemoryError: Java heap
		// space
		// at java.util.Arrays.copyOf(Arrays.java:2760)
		// at java.util.Arrays.copyOf(Arrays.java:2734)
		// at java.util.ArrayList.ensureCapacity(ArrayList.java:167)
		// at java.util.ArrayList.add(ArrayList.java:351)
		// at
		// com.anders.experiment.jvm.heap.OutOfMemory.main(OutOfMemory.java:20)
		while (true) {
			list.add(new OOMObject());
		}
	}
}

class OOMObject {
}