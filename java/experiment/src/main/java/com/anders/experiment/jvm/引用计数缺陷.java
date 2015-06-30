package com.anders.experiment.jvm;

/**
 * 使用VisualVM监视该进程的堆变化，GC后堆内存显著降低，说明JVM没有使用引用计数算法
 * 
 * @author Anders
 * 
 */
public class 引用计数缺陷 {
	// 也可以用jstat -gcutil pid查看内存变化情况
	// S0 S1 E O P YGC YGCT FGC FGCT GCT
	// 0.00 0.00 2.00 74.89 3.09 2 0.001 2 0.008 0.009
	// 0.00 0.00 0.00 0.44 3.10 2 0.001 4 0.015 0.016

	public static void main(String[] args) throws InterruptedException {
		BigObject bigObject1 = new BigObject();
		BigObject bigObject2 = new BigObject();

		bigObject1.instance = bigObject2;
		bigObject2.instance = bigObject1;

		bigObject1 = null;
		bigObject2 = null;

		Thread.sleep(30000);

		System.out.println("ready");

		Thread.sleep(5000);

		new WaitThread().start();
	}
}

class BigObject {

	public Object instance = null;

	private static final int _20MB = 1024 * 1024 * 20;

	public final byte[] bigSize = new byte[_20MB];
}

class WaitThread extends Thread {
	@Override
	public void run() {
		while (true) {
			System.out.println("GC回收");
			System.gc();
			Runtime.getRuntime().gc();
			try {
				sleep(3000);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
