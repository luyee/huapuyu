package prototype.simple;

public class MainClass
{
	public static void main(String[] args)
	{
		// 设计clone时的三个条件
		// x.clone != x
		// x.clone.getClass() == x.getClass()
		// x.clone().equals(x)
		Prototype p1 = new ConcretePrototype();
		System.out.println(p1.getClass().getName() + "\t" + p1.hashCode());

		Prototype p2 = (Prototype) p1.clone();
		System.out.println(p2.getClass().getName() + "\t" + p2.hashCode());

		if (p1.equals(p2))
		{
			System.out.println("true");
		}
		else
		{
			System.out.println("false");
		}
	}
}
