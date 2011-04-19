package chainOfResponsibility.example3;

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
		FilterChain fc1 = new FilterChain();
		fc1.addFilter(new HtmlFilter());
		FilterChain fc2 = new FilterChain();
		fc2.addFilter(new SesitiveFilter());
		fc2.addFilter(fc1);

		MsgProcessor mp = new MsgProcessor();
		mp.setMsg("hello world, <script>, √Ù∏–");
		mp.setFc(fc2);
		String msg = mp.process();
		System.out.println(msg);
	}
}