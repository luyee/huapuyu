package flyweight.simple;

public class ConcreteFlyweight extends Flyweight
{
	private Character intrinsicState = null;

	public ConcreteFlyweight(Character state)
	{
		this.intrinsicState = state;
	}

	@Override
	public void operation(String state)
	{
		System.out.println("Intrinsic State = " + intrinsicState + ", Extrinsic State = " + state + ";");
	}
}
