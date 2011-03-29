package 备忘录模式;

// 看管者，看守人，照顾者
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
