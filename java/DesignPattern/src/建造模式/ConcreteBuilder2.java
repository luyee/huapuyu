package 建造模式;

public class ConcreteBuilder2 extends Builder
{
	@Override
	public void buildBody()
	{
		System.out.println(this.toString() + " : buildBody");
	}

	@Override
	public void buildSubject()
	{
		System.out.println(this.toString() + " : buildSubject");
	}
}
