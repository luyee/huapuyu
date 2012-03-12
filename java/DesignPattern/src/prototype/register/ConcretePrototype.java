package prototype.register;

public class ConcretePrototype implements Prototype {
	public synchronized Object clone() {
		Prototype prototype = null;

		try {
			prototype = (Prototype) super.clone();
			return prototype;
		}
		catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return prototype;
	}
}
