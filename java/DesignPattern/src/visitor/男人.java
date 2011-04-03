package visitor;

public class ÄÐÈË extends Person
{
	@Override
	public void accept(Action visitor)
	{
		visitor.getManConclusion(this);
	}
}
