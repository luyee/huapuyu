package composite;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ≤‚ ‘
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
		Component rootComponent = new Composite();

		Component component1 = new Composite();
		rootComponent.add(component1);

		Component component11 = new Composite();
		component1.add(component11);

		Component component2 = new Composite();
		rootComponent.add(component2);

		Leaf leaf = new Leaf();
		rootComponent.add(leaf);

		rootComponent.sampleOperation(StringUtils.EMPTY);
	}
}