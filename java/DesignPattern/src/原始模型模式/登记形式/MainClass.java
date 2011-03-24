package 原始模型模式.登记形式;

public class MainClass
{
	public static void main(String[] args)
	{
		Prototype p1 = new ConcretePrototype();
		System.out.println(p1.getClass().getName() + p1.hashCode());

		Prototype p2 = (Prototype) p1.clone();
		System.out.println(p2.getClass().getName() + p2.hashCode());
	}
}