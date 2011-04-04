package flyweight.simple;

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
		FlyweightFactory factory = new FlyweightFactory();
		Flyweight fly = factory.factory(new Character('a'));
		fly.operation("first call");
		fly = factory.factory(new Character('b'));
		fly.operation("second call");
		fly = factory.factory(new Character('c'));
		fly.operation("third call");
	}
}