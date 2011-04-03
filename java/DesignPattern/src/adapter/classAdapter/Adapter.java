package adapter.classAdapter;

public class Adapter extends Adaptee implements Target
{
	@Override
	public void sampleOperation2()
	{
		System.out.println("Adapter sampleOperation2");
	}
}
