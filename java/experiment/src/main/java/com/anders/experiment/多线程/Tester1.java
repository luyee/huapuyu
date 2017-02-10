package com.anders.experiment.多线程;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

public class Tester1 {
	private Map<Integer, Integer> idToIdMap;
	private Iterator<Integer> ids = CollectionUtils.EMPTY_COLLECTION.iterator();

	public static void main(String[] args) {
		new Tester1().run();
	}

	public void run() {
		ExecutorService pool = Executors.newFixedThreadPool(2);
		CompletionService<Integer> cs = new ExecutorCompletionService<Integer>(pool);

		try {
			idToIdMap = new HashMap<Integer, Integer>();
			for (int i = 1; i <= 1000; i++) {
				idToIdMap.put(i, i);
			}

			if (MapUtils.isNotEmpty(idToIdMap)) {
				ids = idToIdMap.keySet().iterator();
			}

			for (int i = 0; i < 2; i++) {
				Callable<Integer> hintTransferTask = new CheckSelfHintTask();
				cs.submit(hintTransferTask);
			}

			for (int i = 0; i < 2; i++) {
				System.out.println(cs.take().get());
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}
		finally {
			pool.shutdown();
		}

		System.out.println("ok");
	}

	private synchronized Integer getHintId() {
		if (ids.hasNext()) {
			return ids.next();
		}
		return null;
	}

	private class CheckSelfHintTask implements Callable<Integer> {

		private int i = 0;

		public Integer call() throws Exception {
			Integer hintId = getHintId();

			while (hintId != null) {
				System.out.println(String.format("正在处理第%d条", hintId));

				hintId = getHintId();

				i++;
			}

			return i;
		}

	}
}
