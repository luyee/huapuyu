package 适配器模式.类的适配器模式;

public class MainTest
{
	public static void main(String[] args)
	{
		Target target = new Adapter();
		target.sampleOperation1();
		target.sampleOperation2();
	}
}
