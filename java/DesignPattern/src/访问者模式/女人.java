package 访问者模式;

public class 女人 extends Person
{
	@Override
	public void accept(Action visitor)
	{
		visitor.getWomanConclusion(this);
	}
}
