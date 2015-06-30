package com.anders.ssh.rmi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试synGetRmi，这个单元测试先执行
 * 
 * @author Anders Zhu
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-remote-test.xml" })
public class SynRmiTest1 {
	@Autowired
	private AndersRmi andersRmi;

	@Test
	public void testSynGetRmi() {
		// Assert.assertEquals(true, andersRmi.synGetRmi(true).contains(true));
		Test23 test23 = new Test23();
		test23.setAge(123);
		Test24 test24 = new Test24();
		test24.setTest23(test23);
		test24.setName("14r14");
		setttt(test24);
		System.out.println(test24.getName());
		System.out.println(test24.getTest23().getAge());
	}

	class Test24 {
		private String name;
		private Test23 test23;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Test23 getTest23() {
			return test23;
		}

		public void setTest23(Test23 test23) {
			this.test23 = test23;
		}
	}

	class Test23 {
		private int age;

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

	}

	private void setttt(Test24 test24) {
		test24.setName("zhuzhen");
		test24.getTest23().setAge(111);
	}
}
