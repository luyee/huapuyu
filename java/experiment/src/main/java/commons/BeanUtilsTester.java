package commons;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.SqlDateConverter;

public class BeanUtilsTester {

	public static void main(String[] args) {
		User user1 = new User();
		user1.setId(1L);
		user1.setName("zhuzhen");
		user1.setDesc("hello");
		user1.setTime(new Date());
		System.out.println(user1);

		User user2 = new User();
		try {
			BeanUtils.setProperty(user2, "id", BeanUtils.getProperty(user1, "id"));
			BeanUtils.setProperty(user2, "name", BeanUtils.getProperty(user1, "name"));
			BeanUtils.setProperty(user2, "desc", BeanUtils.getProperty(user1, "desc"));
			BeanUtilsEx.setProperty(user2, "time", BeanUtilsEx.getProperty(user1, "time"));
			System.out.println(BeanUtils.getProperty(user1, "time"));
			System.out.println(BeanUtils.getProperty(user2, "time"));
			System.out.println(BeanUtils.getProperty(user1, "time").equals(BeanUtils.getProperty(user2, "time")));
			System.out.println(user2);
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		try {
			BeanUtils.copyProperties(new User(), new User());
			System.out.println("1");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try {
			BeanUtilsEx.copyProperties(new User(), new User());
			System.out.println("2");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try {
			org.springframework.beans.BeanUtils.copyProperties(new User(), new User());
			System.out.println("3");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}

class BeanUtilsEx extends BeanUtils {

	// private static Map cache = new HashMap();

	private BeanUtilsEx() {
	}

	static {
		ConvertUtils.register(new SqlDateConverter(), java.util.Date.class);
		ConvertUtils.register(new UtilDateConverter(), java.util.Date.class);
	}

	public static void copyProperties(Object target, Object source) throws InvocationTargetException, IllegalAccessException {
		org.apache.commons.beanutils.BeanUtils.copyProperties(target, source);
	}

	public static String getProperty(Object bean, String name) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		return org.apache.commons.beanutils.BeanUtils.getProperty(bean, name);
	}

	public static void setProperty(Object bean, String name, Object value) throws IllegalAccessException, InvocationTargetException {
		org.apache.commons.beanutils.BeanUtils.setProperty(bean, name, value);
	}
}

class UtilDateConverter implements Converter {

	@Override
	public Object convert(Class arg0, Object arg1) {
		System.out.println(arg0);
		System.out.println(arg1);
		String p = (String) arg1;
		if (p == null || p.trim().length() == 0) {
			return null;
		}
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return df.parse(p.trim());
		}
		catch (Exception e) {
			try {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				return df.parse(p.trim());
			}
			catch (ParseException ex) {
				return null;
			}
		}
	}
}
