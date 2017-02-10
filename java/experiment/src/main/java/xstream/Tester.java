package xstream;

import java.util.Date;

import org.junit.Test;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Tester {

	@Test
	public void test1() {
		long beginTime = new Date().getTime();
		// 总运行时间：279
		XStream xstream = new XStream(new DomDriver()); // does not require XPP3 library
		// 总运行时间：173
		// XStream xstream = new XStream(new StaxDriver()); // does not require XPP3 library
		xstream.alias("person", Person.class);
		xstream.alias("phonenumber", PhoneNumber.class);
		Person joe = new Person("Joe", "Walnes");
		joe.setPhone(new PhoneNumber(123, "1234-456"));
		joe.setFax(new PhoneNumber(123, "9999-999"));
		String xml = xstream.toXML(joe);
		System.out.println(xml);
		Person personFrom = (Person) xstream.fromXML(xml);
		System.out.println(personFrom);
		long endTime = new Date().getTime();
		System.out.println("总运行时间：" + (endTime - beginTime));
	}
}
