package 简单工厂模式;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import 工厂方法模式.CreateCarException;

public class 测试
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
	public void 普通工厂类() throws CreateCarException
	{
		ICar 奔驰 = 普通工厂类.factory(汽车品牌.奔驰);
		奔驰.启动();
		奔驰.停止();

		ICar 奥迪 = 普通工厂类.factory(汽车品牌.奥迪);
		奥迪.启动();
		奥迪.停止();

		// Java中的简单工厂模式，SimpleDateFormat和DateFormat
		Date date = new Date();
		System.out.println(SimpleDateFormat.getDateInstance(DateFormat.DEFAULT).format(date));
		System.out.println(SimpleDateFormat.getDateInstance(DateFormat.FULL).format(date));
	}

	@Test
	public void 反射工厂类() throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		ICar 奔驰 = 反射工厂类.getInstance(汽车品牌.奔驰);
		奔驰.启动();
		奔驰.停止();

		ICar 奥迪 = 反射工厂类.getInstance(汽车品牌.奥迪);
		奥迪.启动();
		奥迪.停止();
	}
}
