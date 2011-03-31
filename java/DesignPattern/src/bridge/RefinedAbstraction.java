package bridge;

public class RefinedAbstraction extends Abstraction
{
	@Override
	public void Operation()
	{
		implementor.Operation();
	}
}
