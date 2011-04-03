package singleton;

public class SingletonClass2
{
	private static final SingletonClass2 singletonClass2 = new SingletonClass2();

	private SingletonClass2()
	{
	}

	public static SingletonClass2 getInstance()
	{
		return singletonClass2;
	}
}
