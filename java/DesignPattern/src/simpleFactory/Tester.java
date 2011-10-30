package simpleFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

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
	public void 普通工厂类() throws CreateCarException, SAXException
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

		// XMLReaderFactory和XMLReader是xml-apis.jar中定义的，具体的XML解析jar扩展XMLReader
		String className = "org.apache.xerces.parsers.SAXParser";
		XMLReader xmlReader = XMLReaderFactory.createXMLReader(className);
		System.out.println(xmlReader.getClass().getName());
		className = "org.apache.crimson.parser.XMLReaderImpl";
		xmlReader = XMLReaderFactory.createXMLReader(className);
		System.out.println(xmlReader.getClass().getName());
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
