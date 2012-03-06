package 序列化;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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

	@Test
	public void test() {
		// 通过第一步序列化生成txt文件后，我试着修改该文件，但是反序列化会有问题
		serialization();
		// 如果不改动txt文件，而是改动Person类的代码，反序列化倒是可以的，比如序列化之后，给Person类的setName方法添加一个syso代码，反序列化之后，调用setName时将会执行syso代码
		deserialize();
	}

	private void deserialize() {
		try {
			FileInputStream fis = new FileInputStream("c:\\person.txt");
			ObjectInputStream ois = new ObjectInputStream(fis);
			System.out.println("deserialize");
			Person person = (Person) ois.readObject();
			System.out.println(person);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void serialization() {
		Person person = new Person("Zhu Zhen", 29);
		try {
			FileOutputStream fos = new FileOutputStream("c:\\person.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			System.out.println("serialization");
			oos.writeObject(person);
			System.out.println(person);
			oos.flush();
			oos.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}