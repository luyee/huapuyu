package visitor;

import java.util.ArrayList;
import java.util.List;

public class ObjectStructure
{
	private List<Person> elementList = new ArrayList<Person>();

	public void attach(Person person)
	{
		elementList.add(person);
	}

	public void detach(Person person)
	{
		elementList.remove(person);
	}

	public void display(Action visitor)
	{
		for (Person person : elementList)
		{
			person.accept(visitor);
		}
	}
}
