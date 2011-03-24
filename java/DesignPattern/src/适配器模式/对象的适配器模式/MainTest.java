package 适配器模式.对象的适配器模式;

public class MainTest
{
	public static void main(String[] args)
	{
		Target target = new Adapter(new Adaptee());
		target.sampleOperation1();
		target.sampleOperation2();
	}
}
