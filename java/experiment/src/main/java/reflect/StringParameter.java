package reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 测试反射调用三种参数传递的函数
 * 
 * @author Anders
 * 
 */
public class StringParameter {
	public static void main(String[] args) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		StringParameter stringParameter = new StringParameter();

		Class<?>[] classes = new Class[2];
		classes[0] = Object[].class;
		classes[1] = Object[].class;

		Method method1 = stringParameter.getClass().getMethod("test1", classes);
		// 下面这种方式也可以
		// Method method1 = stringParameter.getClass().getMethod("test1", Object[].class, Object[].class);
		Method method2 = stringParameter.getClass().getMethod("test2", int.class);
		Method method3 = stringParameter.getClass().getMethod("test3", String.class);

		Object[] array1 = { "a", "b", "c" };
		Object[] array2 = new Object[] { "a", "b" };
		Object[] array = new Object[] { array1, array2 };

		method1.invoke(stringParameter, array);

		Object o2 = 2;
		method2.invoke(stringParameter, o2);

		Object o3 = "zhuzhen";
		method3.invoke(stringParameter, o3);
	}

	/**
	 * 参数是数组
	 * 
	 * @param array1
	 * @param array2
	 */
	public void test1(Object[] array1, Object[] array2) {
		System.out.println(array1.length + array2.length);
	}

	/**
	 * 参数是基本类型
	 * 
	 * @param value
	 */
	public void test2(int value) {

		System.out.println(value);
	}

	/**
	 * 参数是引用类型
	 * 
	 * @param value
	 */
	public void test3(String value) {
		System.out.println(value);
	}
}
