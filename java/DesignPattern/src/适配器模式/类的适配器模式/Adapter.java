package 适配器模式.类的适配器模式;

public class Adapter extends Adaptee implements Target
{
	@Override
	public void sampleOperation2()
	{
		System.out.println("Adapter sampleOperation2");
	}
}
