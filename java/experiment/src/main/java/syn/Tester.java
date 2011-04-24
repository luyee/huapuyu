package syn;

public class Tester
{
	public static void main(String[] args)
	{
		SynClass sc = new SynClass();

		MyThread1 thread1 = new MyThread1(sc);
		thread1.start();

		MyThread2 thread2 = new MyThread2(sc);
		thread2.start();
	}
}

class MyThread1 extends Thread
{
	private SynClass sc;

	public MyThread1(SynClass sc)
	{
		this.sc = sc;
	}

	@Override
	public void run()
	{
		sc.method1();
		super.run();
	}
}

class MyThread2 extends Thread
{
	private SynClass sc;

	public MyThread2(SynClass sc)
	{
		this.sc = sc;
	}

	@Override
	public void run()
	{
		sc.method2();
		super.run();
	}
}

class SynClass
{
	public synchronized void method1()
	{
		try
		{
			Thread.sleep(5000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		System.out.println("method1...");
	}

	public synchronized void method2()
	{
		try
		{
			Thread.sleep(5000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		System.out.println("method2...");
	}
}
