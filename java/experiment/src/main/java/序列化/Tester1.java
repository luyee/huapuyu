package 序列化;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.commons.lang.ArrayUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

public class Tester1 {
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
	public void test() {
		// 通过第一步序列化生成xml文件后，我试着修改该文件，反序列化没有问题
		serialization();
		deserialize();
	}

	private void serialization() {
		Person[] persons = new Person[2];
		persons[0] = new Person("Jay", 24);
		persons[1] = new Person("Tom", 23);

		XStream xstream = new XStream();
		try {
			System.out.println("serialization");
			FileOutputStream fos = new FileOutputStream("c:\\person.xml");
			xstream.toXML(persons, fos);
		}
		catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		System.out.println(xstream.toXML(persons));
	}

	private void deserialize() {
		XStream xs = new XStream();
		Person[] persons = null;

		try {
			System.out.println("deserialize");
			FileInputStream fis = new FileInputStream("c:\\person.xml");
			persons = (Person[]) xs.fromXML(fis);
			if (ArrayUtils.isNotEmpty(persons))
				for (Person person : persons)
					System.out.println(person);
		}
		catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
	}
}