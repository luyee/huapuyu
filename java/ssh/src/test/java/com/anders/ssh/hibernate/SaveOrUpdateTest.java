package com.anders.ssh.hibernate;

import java.util.Random;

import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.ssh.bo.test.Account;
import com.anders.ssh.service.AccountService;

/**
 * 多个线程操作同一条记录，如果该记录不存在，则insert，否则则update，测试会不会出现两个线程insert两条记录
 * 
 * @author Anders Zhu
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-test.xml" })
public class SaveOrUpdateTest {
	@Autowired
	private AccountService accountService;

	// @Before
	// public void before() {
	// dataService.deleteById(1L);
	// }
	//
	// @After
	// public void after() {
	// dataService.deleteById(1L);
	// }

	@Test
	public void test() throws Throwable {
		// Data data = dataService.getById(2L);
		// if (data == null) {
		// data = new Data();
		// data.setId(1L);
		// data.setType(Byte.MIN_VALUE);
		// data.setName("zhuzhen");
		// data.setEnable(true);
		// dataService.save(data);
		// }

		// try {
		// for (int i = 0; i < 5; i++) {
		// new LockThread(dataService).start();
		// }
		// }
		// catch (Throwable e) {
		// e.printStackTrace();
		// }

		TestRunnable testRunnable = new TestRunnable() {
			@Override
			public void runTest() throws Throwable {
				if (accountService != null) {
					Account account = accountService.getById(1L);
					if (account == null) {
						account = new Account();
						account.setId(1L);
						account.setName("zhuzhen");
						account.setEnable(true);
					}
					else {
						account.setName(String.valueOf(new Random().nextInt()));
					}
					accountService.saveOrUpdate(account);
				}
			}
		};

		TestRunnable[] testRunnables = { testRunnable, testRunnable, testRunnable, testRunnable, testRunnable };

		MultiThreadedTestRunner multiThreadedTestRunner = new MultiThreadedTestRunner(testRunnables);
		multiThreadedTestRunner.runTestRunnables(1000L);
	}
}

// class LockThread extends Thread {
//
// private DataService dataService;
//
// public LockThread(DataService dataService) {
// this.dataService = dataService;
// }
//
// @Override
// public void run() {
// System.out.println("run...");
// System.out.println(dataService);
// System.out.println("run123...");
// if (dataService != null) {
// Data data = dataService.getById(1L);
// if (data == null) {
// data = new Data();
// data.setId(1L);
// data.setType(Byte.MIN_VALUE);
// data.setName("zhuzhen");
// data.setEnable(true);
// }
// else {
// data.setName(this.getName());
// }
// dataService.saveOrUpdate(data);
// }
// }
// }
