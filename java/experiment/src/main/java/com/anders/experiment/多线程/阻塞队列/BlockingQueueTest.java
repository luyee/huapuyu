package com.anders.experiment.多线程.阻塞队列;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueTest {
	public static void main(String[] args) throws InterruptedException {
		HintTransferQueueProcessor hintTransferQueueProcessor = new HintTransferQueueProcessor();
		hintTransferQueueProcessor.addHint(1L);
		hintTransferQueueProcessor.addHint(2L);
		hintTransferQueueProcessor.addHint(3L);
		hintTransferQueueProcessor.addHint(4L);
		hintTransferQueueProcessor.addHint(5L);
		hintTransferQueueProcessor.addHint(6L);

		Thread.sleep(1000);

		hintTransferQueueProcessor.run();

		Thread.sleep(20000);

		hintTransferQueueProcessor.addHint(7L);
	}
}

class HintTransferQueueProcessor {

	// ArrayBlockingQueue, DelayQueue, LinkedBlockingQueue, PriorityBlockingQueue, SynchronousQueue
	private BlockingQueue<Long> toTransferHintIdQueue = new ArrayBlockingQueue<Long>(2); // 防止无限量的加入队列，设置一个最大值

	public HintTransferQueueProcessor() {
	}

	public void run() {
		for (int i = 0; i < 3; i++) {
			Thread hintTransferThraed = new Thread(new HintTransferThread());
			hintTransferThraed.setName("线索实时唯一性转换线程" + i);
			hintTransferThraed.start();
		}
	}

	public void addHint(Long hintId) throws InterruptedException {
		// 如果用add，会抛出异常java.lang.IllegalStateException: Queue full
		// boolean added = toTransferHintIdQueue.add(hintId);
		// 如果用put，队列满的情况下会等待
		// toTransferHintIdQueue.put(hintId);
		// 不阻塞，如果队列满则返回false，否则为true
		boolean added = toTransferHintIdQueue.offer(hintId);
		if (added) {
			System.out.println("ok");
		} else {
			System.out.println("fail");
		}
		System.out.println("完成添加：" + hintId);
	}

	private class HintTransferThread implements Runnable {
		public void run() {
			while (true) {
				try {
					// peek每次都从队列获取，且不删除获取的元素
					// Long toTransferHintId = toTransferHintIdQueue.peek();
					// poll每次都从队列获取，删除获取的元素，如果队列为空，直接退出，不阻塞
					// Long toTransferHintId = toTransferHintIdQueue.poll();
					// take每次都从队列获取，删除获取的元素，如果队列为空，阻塞到队列不为空
					Long toTransferHintId = toTransferHintIdQueue.take();
					Thread.sleep(5000);
					System.out.println("run : " + toTransferHintId);
					break;
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
}
