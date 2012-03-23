package singleton;

public class SingletonClass {
	private static SingletonClass singletonClass = null;

	private SingletonClass() {
	}

	public static SingletonClass getInstance() {
		if (null == singletonClass) {
			synchronized (SingletonClass.class) {
				if (null == singletonClass) {
					singletonClass = new SingletonClass();
				}
			}
		}

		return singletonClass;
	}
}
