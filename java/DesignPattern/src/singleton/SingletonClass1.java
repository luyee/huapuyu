package singleton;

public class SingletonClass1 {
	private static final SingletonClass1 singletonClass1 = new SingletonClass1();

	private SingletonClass1() {
	}

	public static SingletonClass1 getInstance() {
		return singletonClass1;
	}
}
