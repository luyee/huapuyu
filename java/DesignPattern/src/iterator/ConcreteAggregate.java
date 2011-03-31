package iterator;

public class ConcreteAggregate extends Aggregate
{
	private Object[] objArray = { "Guo Lili", "Zhu Zhen", "Zhu Rongbao" };

	@Override
	public Iterator createIterator()
	{
		return new ConcreteIterator(this);
	}

	public Object getElement(int index)
	{
		if (index < objArray.length && index >= 0)
		{
			return objArray[index];
		}
		else
		{
			return null;
		}
	}

	public int size()
	{
		return objArray.length;
	}
}
