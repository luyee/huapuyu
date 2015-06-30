package reflect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Test {
	public static void main(String[] args) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Object father = new Son();
		System.out.println(((Son) father).getName());
		Field field = father.getClass().getDeclaredField("name");
		field.setAccessible(true);
		field.set(father, "guolili");
		System.out.println(((Son) father).getName());
		List<Object> list = new ArrayList<Object>();
		list.add(new Son());
		list.add(new Integer(0));
		for (Object object : list) {
			System.out.println(object.getClass().getName());
		}
		System.out.println(father.getClass().getName());
	}
}

class Father {

}

class Son extends Father {
	private String name = "zhuzhen";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
