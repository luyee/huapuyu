package state;

public class 曾侯乙编钟
{
	private 钟 state;

	public void 打击()
	{
		state.打击();
	}

	public void setState(钟 state)
	{
		this.state = state;
	}
}
