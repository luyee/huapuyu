package test.junit.task.executor;

public class Thread2 extends Thread
{
	private String name;

	public Thread2(String name)
	{
		this.name = name;
		setDaemon(false);
	}

	@Override
	public void run()
	{
		for (int i = 0; i < 50000000; i++)
		{
			int j = 5 / 3;
		}
		System.out.println(name);
		super.run();
	}
}
