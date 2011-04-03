package composite;

public class Leaf implements Component
{

	@Override
	public Component getComponent()
	{
		return this;
	}

	@Override
	public void sampleOperation(String msg)
	{
		System.out.println(msg + this.getClass().getName());
	}

	@Override
	public void add(Component component)
	{
		throw new RuntimeException("Can't invoke this method!");
	}

	@Override
	public void remove(Component component)
	{
		throw new RuntimeException("Can't invoke this method!");
	}

}
