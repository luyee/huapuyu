package jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆溢出（VM参数：-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError， 说明：限制Java堆的大小为20MB，不可扩展（将堆的最小值-Xms和最大值-Xmx设置为一样，即可避免堆自动扩展），同时通过-XX:+HeapDumpOnOutOfMemoryError可以让虚拟机在出现内存溢出异常时Dump出当前的内存堆转储快照）
 * 
 * @author Anders Zhu
 * 
 */
public class Heap {

	public static void main(String[] args) {
		List<Test> list = new ArrayList<Test>();

		int i = 1;
		while (true) {
			System.out.println(i++);
			list.add(new Test());
		}
	}
}

class Test {
}