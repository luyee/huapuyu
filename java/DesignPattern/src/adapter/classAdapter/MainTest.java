package adapter.classAdapter;

public class MainTest
{
	public static void main(String[] args)
	{
		Target target = new Adapter();
		target.sampleOperation1();
		target.sampleOperation2();
	}
}
