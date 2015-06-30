package 单测;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;

public class UserDaoTest1 {

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

	@Test
	public void testSave() {
		Assert.assertTrue(true);
	}

	@Test
	public void testUpdate() {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetAll() {
		Assert.assertTrue(true);
	}

	@Test
	@Ignore("忽略不计")
	public void testIgnore() throws InterruptedException {
		Thread.sleep(5000);
	}

	@Test(timeout = 3000)
	@Ignore("忽略不计")
	public void testTimeout() throws InterruptedException {
		Thread.sleep(5000);
	}

	@SuppressWarnings("unchecked")
	@Test(timeout = 3000)
	public void testHamcrest() {
		List<String> names = new ArrayList<String>();
		names.add("朱振");
		names.add("one");
		Assert.assertThat(names, JUnitMatchers.hasItem(anyOf(equalTo("朱振"), equalTo("two"))));
	}
}
