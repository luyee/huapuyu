package reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainClass {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
		try {
			System.out.println(TestClass.class.getName());
			Class a = Class.forName(TestClass.class.getName());
			// Class a = Class.forName("test.TestClass");

			// invoke construtor without parameter
			Object o = a.newInstance();

			// invoke construtor with parameter
			Class[] cTypes = new Class[] { String.class, int.class };
			Constructor ctor = a.getConstructor(cTypes);
			Object[] arg = new Object[] { "zhuzhen", 29 };
			Object obj = ctor.newInstance(arg);

			Constructor ctor1 = a.getConstructor(String.class, int.class);
			Object obj1 = ctor.newInstance("guolili", 26);

			// print all function name
			// 获得定义的方法，能够获得私有的方法
			// Method[] ms = obj1.getClass().getDeclaredMethods();
			// 能够获得父类的方法
			Method[] ms = obj1.getClass().getMethods();
			System.out.println("----------------------------------------");
			for (Method m : ms) {
				System.out.println(m.getName());
			}
			System.out.println("----------------------------------------");

			// invoke function without parameter without return value
			Method m2 = a.getMethod("PrintMsg");
			m2.invoke(o);

			// invoke function with parameter without return value
			Class[] ptypes = new Class[2];
			ptypes[0] = Class.forName("java.lang.String");
			ptypes[1] = int.class;
			Method m1 = a.getMethod("PrintMsg", ptypes);
			Object[] arg1 = new Object[] { "guolili", 29 };
			m1.invoke(o, arg1);

			// invoke function with parameter with return value
			Class[] dtypes = new Class[1];
			dtypes[0] = Class.forName("java.lang.String");
			Method m3 = a.getMethod("PrintMsg", dtypes);
			Object[] arg2 = new Object[] { "guolili" };
			String value = m3.invoke(o, arg2).toString();
			System.out.println(value);

			// invoke property
			Field f = a.getDeclaredField("name");

			System.out.println("-----------------field-----------------------");
			Field[] f1 = a.getFields();
			for (Field ff : f1) {
				System.out.println(ff.getName());
			}
			System.out.println("-----------------field-----------------------");
			Field[] f2 = a.getDeclaredFields();
			for (Field ff : f2) {
				System.out.println(ff.getName());
			}
			System.out.println("-----------------field-----------------------");

			f.setAccessible(true);
			f.set(o, "zhuzhen");
			System.out.println(f.get(o));

			System.out.println("-----------------annotation-----------------------");
			Annotation[] anno = a.getDeclaredAnnotations();
			for (Annotation aaa : anno) {
				System.out.println(aaa.annotationType().getName());
			}

		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
