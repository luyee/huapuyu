package com.anders.experiment.jvm.工具使用;

/**
 * VM参数：-Xms20m -Xmx20m -XX:+HeapDumpOnCtrlBreak -javaagent:D:/code/java/experiment/lib/SizeOf.jar
 * 限制Java堆的大小为20MB，不可扩展（将堆的最小值-Xms和最大值-Xmx设置为一样，即可避免堆自动扩展）
 * 同时通过-XX:+HeapDumpOnCtrlBreak可以使用Ctrl+Break让虚拟机Dump出当前的内存堆转储快照
 * 
 * @author Anders Zhu
 * 
 */
public class Tester {
	// jps -v -l
	public static void main(String[] args) throws InterruptedException {
		Thread.sleep(1000 * 60 * 60);
	}

}
