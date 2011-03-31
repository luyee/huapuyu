package iterator;

/*
 * ¾ßÌå¾Û¼¯
 */
public class ConcreteAggregate implements Aggregate
{
	private Object[] objArray = { "Guo Lili", "Zhu Zhen", "Zhu Rongbao" };

	@Override
	public Iterator iterator()
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
