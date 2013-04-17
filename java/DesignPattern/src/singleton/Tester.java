package singleton;

import java.io.IOException;

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
	public void test懒汉() {
		System.out.println(懒汉.getInstance().toString());
		System.out.println(懒汉.getInstance().toString());
		System.out.println(懒汉.getInstance().toString());
	}

	@Test
	public void test饿汉() throws IOException {
		System.out.println(饿汉.getInstance().toString());
		System.out.println(饿汉.getInstance().toString());
		System.out.println(饿汉.getInstance().toString());

		// Java中非常典型的饿汉单例模式
		Runtime runtime = Runtime.getRuntime();
		runtime.exec("notepad.exe");
	}
}