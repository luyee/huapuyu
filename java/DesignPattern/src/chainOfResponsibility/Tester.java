package chainOfResponsibility;

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
		Handler 班长 = new 班长("班长");
		Handler 排长 = new 排长("排长");
		Handler 连长 = new 连长("连长");
		班长.setNextHandler(排长);
		排长.setNextHandler(连长);
		班长.handleRequest("集合全班士兵");
		班长.handleRequest("集合全排士兵");
		班长.handleRequest("集合全连士兵");
	}
}