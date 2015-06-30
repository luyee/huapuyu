package iterator;

/*
 * 迭代子
 */
public interface Iterator
{
	public void first();

	public void next();

	public boolean isDone();

	public Object currentItem();
}
