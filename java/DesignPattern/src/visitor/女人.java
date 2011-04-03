package visitor;

public class ≈Æ»À extends Person
{
	@Override
	public void accept(Action visitor)
	{
		visitor.getWomanConclusion(this);
	}
}
