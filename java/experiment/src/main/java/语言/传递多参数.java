package 语言;

public class 传递多参数
{
	public static void main(String[] args)
	{
		new 传递多参数().test(1, 2, 3, 4, 5);
	}

	public void test(int... params)
	{
		for (int i : params)
		{
			System.out.println(i);
		}
	}
}
