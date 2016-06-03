package com.anders.ethan.log.cat.test;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Transaction;

public class CatTest {

	private static Logger logger = LoggerFactory.getLogger(CatTest.class);

	public static void main(String[] args) {
		Transaction t1 = Cat.newTransaction("Check11", "name");
		Transaction t3 = Cat.newTransaction("Check21", "name");
		// for (int i = 0; i < 2080; i++) {
		Transaction t4 = Cat.newTransaction("Check31", "name");
		t4.complete();
		// }
		t3.complete();
		t1.complete();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// for (int i = 0; i < 100000; i++) {
		Transaction t = Cat.newTransaction("URL123456" + (10),
				"http://127.0.0.1");
		try {
			// logger.error("adcfasdfas", new RuntimeException("afasdfas"));
			// yourBusinessOperation();
			// System.out.println("hello world");
			// Cat.getProducer().logEvent("your event type",
			// "your event name",
			// Event.SUCCESS, "keyValuePairs");
			Cat.logEvent("URL55.Server", "192.168.56.102", Event.SUCCESS, "");
			Cat.logMetricForCount("maxsize");
			// Cat.newHeartbeat("ok", "ok");
			t.setStatus(Transaction.SUCCESS);
		} catch (Exception e) {
			// Cat.getProducer().logError(e);//用log4j记录系统异常，以便在Logview中看到此信息
			t.setStatus(e);
			throw e;

		} finally {
			t.complete();
		}
		// }

		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
