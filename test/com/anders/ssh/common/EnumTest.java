package com.anders.ssh.common;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EnumTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test1() {
		System.out.println("hello : " + Period.LONG + ", end!");
		System.out.println(Period.LONG.getClass().getName());

		Map<String, String> map = new HashMap<String, String>();
		map.put("短期", "短期");
		System.out.println(map.get(Period.SHORT));
		System.out.println(map.get(Period.SHORT.toString()));
	}

	public enum Period {

		SHORT("短期", 0), LONG("长期", 1);

		private Integer value;
		private String label;

		private Period(String label, Integer value) {
			this.label = label;
			this.value = value;
		}

		public String getLabel() {
			return this.label;
		}

		public Integer getValue() {
			return this.value;
		}

		@Override
		public String toString() {
			return this.label;
		}

	}
}
