package iterator;

/*
 * 具体迭代子
 */
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
	public boolean isDone()
	{
		return index >= size;
	}

	@Override
	public Object currentItem()
	{
		return aggregate.getElement(index);
	}

}
