package assertp;

import junit.framework.AssertionFailedError;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Tester {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected = AssertionFailedError.class)
	public void test1() {
		String name = null;
		junit.framework.Assert.assertNull(name, "name is null");
	}

	@Test(expected = IllegalArgumentException.class)
	public void test2() {
		String name = null;
		org.springframework.util.Assert.notNull(name, "name is null");
	}

}
