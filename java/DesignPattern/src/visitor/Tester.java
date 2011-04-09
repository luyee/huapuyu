package visitor;

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
		ObjectStructure objectStructure = new ObjectStructure();
		objectStructure.attach(new 男人());
		objectStructure.attach(new 女人());

		成功 success = new 成功();
		objectStructure.display(success);

		失败 failure = new 失败();
		objectStructure.display(failure);

		恋爱 love = new 恋爱();
		objectStructure.display(love);

	}
}