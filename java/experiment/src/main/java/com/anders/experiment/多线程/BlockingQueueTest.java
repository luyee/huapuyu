package com.anders.experiment.多线程;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueTest {
	public static void main(String[] args) {
		// try {
		// System.out.println("begin");
		// new BlockingQueueTest().toTransferHintIdQueue.take();
		// System.out.println("end");
		// }
		// catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		HintTransferQueueProcessor hintTransferQueueProcessor = new HintTransferQueueProcessor();
		hintTransferQueueProcessor.addHint(123L);
		hintTransferQueueProcessor.addHint(1234L);
	}
}

class HintTransferQueueProcessor {

	private BlockingQueue<Long> toTransferHintIdQueue = new ArrayBlockingQueue<Long>(1000000); // 防止无限量的加入队列，设置一个最大值

	public HintTransferQueueProcessor() {
		for (int i = 0; i < 2; i++) {
			Thread hintTransferThraed = new Thread(new HintTransferThread());
			hintTransferThraed.setName("线索实时唯一性转换线程" + i);
			hintTransferThraed.start();
		}

	}

	public void addHint(Long hintId) {

		boolean added = toTransferHintIdQueue.offer(hintId); // 非阻塞的加入hintId，即使队列已满，也不做处理
		if (added) {
			System.out.println("ok");
		}
		else {
			System.out.println("fail");
		}
	}

	private class HintTransferThread implements Runnable {
		public void run() {
			while (true) {
				try {
					Long toTransferHintId = toTransferHintIdQueue.take();
					System.out.println("run");
				}
				catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}
				catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}

	}
}
