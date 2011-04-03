package builder;

public class ConcreteBuilder1 extends Builder
{
	@Override
	public void 内容()
	{
		System.out.println(this.toString() + " : 内容1");
	}

	@Override
	public void 主题()
	{
		System.out.println(this.toString() + " : 主题1");
	}
}
