package singleton;

public class SingletonClass
{
	private static SingletonClass singletonClass = null;

	// private static final SingletonClass singletonClass = new SingletonClass();

	private SingletonClass()
	{
	}

	public static SingletonClass getInstance()
	{
		if (null == singletonClass)
		{
			synchronized (SingletonClass.class)
			{
				if (null == singletonClass)
				{
					singletonClass = new SingletonClass();
				}
			}
		}

		return singletonClass;
	}

	// public static SingletonClass getInstance()
	// {
	// return singletonClass;
	// }
}
