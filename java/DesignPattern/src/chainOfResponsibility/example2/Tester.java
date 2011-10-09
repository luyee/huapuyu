package chainOfResponsibility.example2;

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
		FilterChain fc = new FilterChain();
		fc.addFilter(new HtmlFilter()).addFilter(new SesitiveFilter());

		MsgProcessor mp = new MsgProcessor();
		mp.setMsg("hello world, <script>, 敏感");
		mp.setFc(fc);
		String msg = mp.process();
		System.out.println(msg);
	}
}