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

public class Tester2 {
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
		serialization("c:\\myExternalizable.txt");
		deserialize("c:\\myExternalizable.txt");
	}

	private void serialization(String fileName) {
		try {
			System.out.println("序列化");
			// 创建一个对象输出流，讲对象输出到文件
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
			MyExternalizable myExternalizable = new MyExternalizable("renyanwei", "888888", 20);
			out.writeObject(myExternalizable); // 序列化一个会员对象
			out.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void deserialize(String fileName) {
		try {
			System.out.println("反序列化");
			// 创建一个对象输入流，从文件读取对象
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
			// 读取UserInfo对象并调用它的toString()方法
			MyExternalizable myExternalizable = (MyExternalizable) (in.readObject());
			System.out.println(myExternalizable.toString());
			in.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}