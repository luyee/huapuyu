package templateMethod;

public class PlayChina extends PlayPES
{
	@Override
	protected void chooseShirt()
	{
		System.out.println("����");
	}

	@Override
	protected void chooseTeam()
	{
		System.out.println("�й�");
	}
}