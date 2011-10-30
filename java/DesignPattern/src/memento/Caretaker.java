package memento;

/*
 * 看管者，看守人，照顾者，假设为：游戏记录管理器
 */
public class Caretaker
{
	private Memento memento;

	public Memento retrieveMemento()
	{
		return memento;
	}

	public void saveMemento(Memento memento)
	{
		this.memento = memento;
	}
}
