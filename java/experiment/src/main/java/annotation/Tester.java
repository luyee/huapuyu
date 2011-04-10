package annotation;

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
		System.out.println("setUpBeforeClass");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		System.out.println("tearDownAfterClass");
	}

	@Before
	public void setUp() throws Exception
	{
		System.out.println("setUp");
	}

	@After
	public void tearDown() throws Exception
	{
		System.out.println("tearDown");
	}

	@Test
	public void test3() throws SecurityException, NoSuchMethodException, NoSuchFieldException
	{
		Parser parser = new Parser();
		parser.parser();
	}
}
