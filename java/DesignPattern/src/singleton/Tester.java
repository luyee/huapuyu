package singleton;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Tester
{
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}

	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void test()
	{
		System.out.println(SingletonClass.getInstance().toString());
		System.out.println(SingletonClass.getInstance().toString());
		System.out.println(SingletonClass.getInstance().toString());

		// Java中的非常典型的单例模式
		try
		{
			Runtime.getRuntime().exec("notepad.exe");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}