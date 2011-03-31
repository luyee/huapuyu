package iterator;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/*
 * TestNG貌似不支持中文的包和类，如果包和类改成中文会报错，JUnit没这个问题
 */
public class Tester
{
	@BeforeMethod
	public void beforeMethod()
	{
		System.out.println("beforeMethod");
	}

	@AfterMethod
	public void afterMethod()
	{
		System.out.println("afterMethod");
	}

	@BeforeClass
	public void beforeClass()
	{
		System.out.println("beforeClass");
	}

	@AfterClass
	public void afterClass()
	{
		System.out.println("afterClass");
	}

	@BeforeTest
	public void beforeTest()
	{
		System.out.println("beforeTest");
	}

	@AfterTest
	public void afterTest()
	{
		System.out.println("afterTest");
	}

	@BeforeSuite
	public void beforeSuite()
	{
		System.out.println("beforeSuite");
	}

	@AfterSuite
	public void afterSuite()
	{
		System.out.println("afterSuite");
	}

	@Test
	public void test()
	{
		Aggregate aggregate = new ConcreteAggregate();
		Iterator iterator = aggregate.createIterator();
		while (!iterator.isLast())
		{
			System.out.println(iterator.current().toString());
			iterator.next();
		}
	}

}
