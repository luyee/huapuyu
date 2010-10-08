package common;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Utils
{
	private final static Log log = LogFactory.getLog(Utils.class);

	/**
	 * 获得类的泛型类型
	 * 
	 * @param clazz：类的Class
	 * @param index：泛型的索引
	 * @return
	 */
	public static Class<?> getSuperClassGenricType(final Class<?> clazz, final int index)
	{
		Type type = clazz.getGenericSuperclass();

		if (!(type instanceof ParameterizedType))
		{
			log.warn(clazz.getSimpleName() + "'s super class is not ParameterizedType!");
		}

		Type[] params = ((ParameterizedType) type).getActualTypeArguments();

		if (index >= params.length || index < 0)
		{
			log.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: " + params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class))
		{
			log.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}

		return (Class<?>) params[index];
	}
}
