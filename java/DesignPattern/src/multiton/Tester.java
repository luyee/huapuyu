package multiton;

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
		System.out.println(Multiton.getInstance(1).toString());
		System.out.println(Multiton.getInstance(1).toString());
		System.out.println(Multiton.getInstance(1).toString());
		System.out.println(Multiton.getInstance(2).toString());
		System.out.println(Multiton.getInstance(2).toString());
		System.out.println(Multiton.getInstance(2).toString());
	}
}