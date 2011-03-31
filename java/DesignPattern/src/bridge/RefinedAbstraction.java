package bridge;

/*
 * 修正抽象化
 */
public class RefinedAbstraction extends Abstraction
{
	@Override
	public void Operation()
	{
		// implementor.Operation();
		System.out.println("修正抽象化角色的实现");
	}
}
