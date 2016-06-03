package com.anders.ethan.log.cat.test;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Transaction;
import com.dianping.cat.message.spi.MessageTree;
import com.dianping.cat.message.spi.internal.DefaultMessageTree;

public class Cat1Test {

	private static Logger logger = LoggerFactory.getLogger(Cat1Test.class);

	public static void main(String[] args) {
		Transaction t = Cat.newTransaction("PigeonService", "yyyy");

		Cat.logEvent("PigeonService.client", "127.0.0.1");

		MessageTree tree = Cat.getManager().getThreadLocalMessageTree();

		((DefaultMessageTree) tree).setDomain("baidu");
		((DefaultMessageTree) tree).setIpAddress("192.168.0.100");
		t.setStatus(Transaction.SUCCESS);
		t.complete();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
