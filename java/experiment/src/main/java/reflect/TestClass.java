package reflect;

public class TestClass
{
	private String name;

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}

	public TestClass()
	{
		System.out.println("construtor");
	}

	public TestClass(String name, int age)
	{
		System.out.println("construtor | Name : " + name + "; Age : " + String.valueOf(age) + "!");
	}

	public void PrintMsg()
	{
		System.out.println("PringMsg");
	}

	public void PrintMsg(String name, int age)
	{
		System.out.println("PringMsg | Name : " + name + "; Age : " + String.valueOf(age) + "!");
	}

	public String PrintMsg(String name)
	{
		return "PringMsg | Name : " + name + "!";
	}

	@SuppressWarnings("unused")
	private String test(String name)
	{
		return "private!";
	}
}
