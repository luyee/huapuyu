package 语言;

import static org.junit.Assert.assertNotNull;
import static 语言.MyAssert.print2;

import java.util.Date;

import org.junit.Assert;

public class ImportStatic {
	public static void main(String[] args) {
		Assert.assertNull(null);
		assertNotNull("null", new Date());
		MyAssert.print1();
		print2();
	}
}

class MyAssert {
	public static void print1() {
		System.out.println("print1");
	}

	public static void print2() {
		System.out.println("print2");
	}
}
