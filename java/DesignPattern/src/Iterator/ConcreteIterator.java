package Iterator;

public class ConcreteIterator implements Iterator
{
	private ConcreteAggregate aggregate;
	private int index = 0;
	private int size = 0;

	public ConcreteIterator(ConcreteAggregate aggregate)
	{
		this.aggregate = aggregate;
		this.size = aggregate.size();
		this.index = 0;
	}

	@Override
	public void first()
	{
		index = 0;

	}

	@Override
	public void next()
	{
		if (index < size)
			index++;

	}

	@Override
	public boolean isLast()
	{
		return index >= size;
	}

	@Override
	public Object current()
	{
		return aggregate.getElement(index);
	}

}
