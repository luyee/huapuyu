package 访问者模式;

public class 男人 extends Person
{
	@Override
	public void accept(Action visitor)
	{
		visitor.getManConclusion(this);
	}
}
