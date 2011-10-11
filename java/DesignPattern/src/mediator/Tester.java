package mediator;

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
		联合国安理会 unsc = new 联合国安理会();
		美国 usa = new 美国(unsc);
		伊拉克 iraq = new 伊拉克(unsc);
		unsc.setUsa(usa);
		unsc.setIraq(iraq);

		usa.Declare("萨达姆下台，否则我们进攻伊拉克");
		iraq.Declare("我们绝不对投降");
	}
}