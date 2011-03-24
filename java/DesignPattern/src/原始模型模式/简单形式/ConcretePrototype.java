package 原始模型模式.简单形式;

public class ConcretePrototype implements Prototype
{
	public Object clone()
	{
		try
		{
			return super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
