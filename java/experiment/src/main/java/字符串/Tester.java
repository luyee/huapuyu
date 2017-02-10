package 字符串;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Tester {

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
	public void test() {
		// 线程安全
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("this ");
		stringBuffer.append("is ");
		stringBuffer.append("stringbuffer");
		System.out.println(stringBuffer.toString());

		// 非线程安全，但是速度比StringBuffer稍快
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("this ");
		stringBuilder.append("is ");
		stringBuilder.append("stringBuilder");
		System.out.println(stringBuilder.toString());

		String str = "";
		String[] strs = str.split(",");
		System.out.println(strs.length);
		for (String s : strs) {
			System.out.println("****" + s + "****");
		}
	}
}
