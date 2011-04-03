package memento;

// 纪念物，令人回忆的东西，纪念品
public class Memento
{
	private String state;

	public Memento(String state)
	{
		this.state = state;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

}
