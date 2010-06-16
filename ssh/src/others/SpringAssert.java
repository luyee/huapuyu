package others;

import org.springframework.util.Assert;

public class SpringAssert
{
	public static void main(String[] args)
	{
		try
		{
			assertMethod();
		}
		catch (IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
		}
	}

	private static void assertMethod()
	{
		String name = null;
		boolean value = false;
		Assert.isTrue(value, "boolean error");
		Assert.notNull(name, "null error");
		System.out.println("name is not null");
	}
}
