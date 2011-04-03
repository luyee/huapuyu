package templateMethod;

public abstract class PlayPES
{
	/**
	 * 开踢
	 */
	public void play()
	{
		chooseTeam();
		chooseShirt();
		chooseFormation();
	}

	/**
	 * 选择球队
	 */
	protected abstract void chooseTeam();

	/**
	 * 选择球衣
	 */
	protected abstract void chooseShirt();

	/**
	 * 选择阵型
	 */
	private final void chooseFormation()
	{
		System.out.println("442");
	}
}
