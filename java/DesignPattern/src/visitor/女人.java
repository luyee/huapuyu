package visitor;

public class 女人 extends Person
{
	@Override
	public void accept(Action visitor)
	{
		visitor.getWomanConclusion(this);
	}
}
