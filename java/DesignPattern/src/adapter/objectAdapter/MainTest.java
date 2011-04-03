package adapter.objectAdapter;

public class MainTest
{
	public static void main(String[] args)
	{
		Target target = new Adapter(new Adaptee());
		target.sampleOperation1();
		target.sampleOperation2();
	}
}
