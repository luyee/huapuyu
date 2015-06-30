package visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * ObjectStruture
 * 
 * @author Anders Zhu
 * 
 */
public class ObjectStructure {
	private List<Person> persons;

	// private List<Person> person = new ArrayList<Person>();

	public void attach(Person person) {
		persons.add(person);
	}

	public void detach(Person person) {
		persons.remove(person);
	}

	public void display(Action visitor) {
		for (Person person : persons) {
			person.accept(visitor);
		}
	}

	public ObjectStructure(List<Person> persons) {
		this.persons = persons;
	}

	public ObjectStructure() {
		persons = new ArrayList<Person>();
	}
}
