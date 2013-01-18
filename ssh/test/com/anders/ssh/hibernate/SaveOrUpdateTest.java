package com.anders.ssh.hibernate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.ssh.bo.xml.Data;
import com.anders.ssh.service.DataService;

/**
 * 多个线程操作同一条记录，如果该记录不存在，则insert，否则则update，测试会不会出现两个线程insert两条记录
 * 
 * @author Anders Zhu
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-test.xml" })
public class SaveOrUpdateTest {
	@Autowired
	private DataService dataService;

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
	public void test1() {
		for (int i = 0; i < 5; i++) {
			new LockThread(dataService).start();
		}
	}
}

class LockThread extends Thread {

	private DataService dataService;

	public LockThread(DataService dataService) {
		this.dataService = dataService;
	}

	@Override
	public void run() {
		if (dataService != null) {
			Data data = dataService.getById(1L);
			if (data == null) {
				data = new Data();
				data.setId(1L);
				data.setType(Byte.MIN_VALUE);
				data.setName("zhuzhen");
				data.setEnable(true);
			}
			else {
				data.setName(this.getName());
			}
			dataService.saveOrUpdate(data);
		}
	}
}
