package 排序;

import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ChineseTester {
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
		// 中文排序
		String[] userArray = { "爱情", "长岛", "长大", "纽约", "abc", "x", "朱振", "重庆", "红色", "成都" };
		Comparator<Object> comparator = Collator.getInstance(java.util.Locale.CHINA);
		Arrays.sort(userArray, comparator);
		for (String name : userArray)
			System.out.println(name);
	}
}