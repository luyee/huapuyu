package 语言;

public class Finalize {

	public static void main(String[] args) throws Throwable {
		if (true) {
			// 创建匿名对象，执行System.gc()，析构函数能被调用
			new TestClass1().finalize();
		}

		if (true) {
			// 创建非匿名对象，执行System.gc()，析构函数不能被调用
			TestClass2 testClass2 = new TestClass2();
			testClass2.finalize();
			testClass2.print();
		}

		// 不执行System.gc()，析构函数无法被调用
		System.gc();
		Thread.sleep(3000);
	}
}

class TestClass1 {
	public TestClass1() {
		System.out.println("构造函数1");
	}

	public void print() {
		System.out.println("打印1");
	}

	@Override
	protected void finalize() throws Throwable {
		System.out.println("析构函数1");
		super.finalize();
	}
}

class TestClass2 {
	public TestClass2() {
		System.out.println("构造函数2");
	}

	public void print() {
		System.out.println("打印2");
	}

	@Override
	protected void finalize() throws Throwable {
		System.out.println("析构函数2");
		super.finalize();
	}
}
