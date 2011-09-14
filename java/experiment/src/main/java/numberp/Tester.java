package numberp;

import org.apache.commons.lang.math.NumberUtils;

public class Tester {

	public static void main(String[] args) {
		Long l = Long.MIN_VALUE;
		Integer i = Integer.MIN_VALUE;
		Byte b = Byte.MIN_VALUE;
		Short s = Short.MIN_VALUE;
		Double d = Double.MIN_VALUE;
		Float f = Float.MIN_VALUE;
		System.out.println(l + ":" + min(l));
		System.out.println(i + ":" + min(i));
		System.out.println(b + ":" + min(b));
		System.out.println(s + ":" + min(s));
		System.out.println(d + ":" + min(d));
		System.out.println(f + ":" + min(f));

		System.out.println("********************");

		l = Long.MAX_VALUE;
		i = Integer.MAX_VALUE;
		b = Byte.MAX_VALUE;
		s = Short.MAX_VALUE;
		d = Double.MAX_VALUE;
		f = Float.MAX_VALUE;
		System.out.println(l + ":" + max(l));
		System.out.println(i + ":" + max(i));
		System.out.println(b + ":" + max(b));
		System.out.println(s + ":" + max(s));
		System.out.println(d + ":" + max(d));
		System.out.println(f + ":" + max(f));

		System.out.println("********************");

		l = NumberUtils.LONG_ONE;
		i = NumberUtils.INTEGER_ONE;
		b = NumberUtils.BYTE_ONE;
		s = NumberUtils.SHORT_ONE;
		d = NumberUtils.DOUBLE_ONE;
		f = NumberUtils.FLOAT_ONE;
		System.out.println(l + ":" + one(l));
		System.out.println(i + ":" + one(i));
		System.out.println(b + ":" + one(b));
		System.out.println(s + ":" + one(s));
		System.out.println(d + ":" + one(d));
		System.out.println(f + ":" + one(f));

		System.out.println("********************");

		l = NumberUtils.LONG_ZERO;
		i = NumberUtils.INTEGER_ZERO;
		b = NumberUtils.BYTE_ZERO;
		s = NumberUtils.SHORT_ZERO;
		d = NumberUtils.DOUBLE_ZERO;
		f = NumberUtils.FLOAT_ZERO;
		System.out.println(l + ":" + zero(l));
		System.out.println(i + ":" + zero(i));
		System.out.println(b + ":" + zero(b));
		System.out.println(s + ":" + zero(s));
		System.out.println(d + ":" + zero(d));
		System.out.println(f + ":" + zero(f));

		System.out.println("********************");

		l = NumberUtils.LONG_MINUS_ONE;
		i = NumberUtils.INTEGER_MINUS_ONE;
		b = NumberUtils.BYTE_MINUS_ONE;
		s = NumberUtils.SHORT_MINUS_ONE;
		d = NumberUtils.DOUBLE_MINUS_ONE;
		f = NumberUtils.FLOAT_MINUS_ONE;
		System.out.println(l + ":" + minusOne(l));
		System.out.println(i + ":" + minusOne(i));
		System.out.println(b + ":" + minusOne(b));
		System.out.println(s + ":" + minusOne(s));
		System.out.println(d + ":" + minusOne(d));
		System.out.println(f + ":" + minusOne(f));
	}

	private static boolean min(Number number) {
		if (number instanceof Long)
			return number.doubleValue() == Long.MIN_VALUE;
		else if (number instanceof Integer)
			return number.doubleValue() == Integer.MIN_VALUE;
		else if (number instanceof Byte)
			return number.doubleValue() == Byte.MIN_VALUE;
		else if (number instanceof Short)
			return number.doubleValue() == Short.MIN_VALUE;
		else if (number instanceof Double)
			return number.doubleValue() == Double.MIN_VALUE;
		else if (number instanceof Float)
			return number.doubleValue() == Float.MIN_VALUE;
		else
			throw new IllegalArgumentException();
		// try {
		// return Double.MIN_VALUE == number.doubleValue();
		// }
		// catch (NumberFormatException ex) {
		// System.out.println(ex.getMessage());
		// return Boolean.FALSE;
		// }
	}

	private static boolean max(Number number) {
		if (number instanceof Long)
			return number.doubleValue() == Long.MAX_VALUE;
		else if (number instanceof Integer)
			return number.doubleValue() == Integer.MAX_VALUE;
		else if (number instanceof Byte)
			return number.doubleValue() == Byte.MAX_VALUE;
		else if (number instanceof Short)
			return number.doubleValue() == Short.MAX_VALUE;
		else if (number instanceof Double)
			return number.doubleValue() == Double.MAX_VALUE;
		else if (number instanceof Float)
			return number.doubleValue() == Float.MAX_VALUE;
		else
			throw new IllegalArgumentException();
		// try {
		// return Double.MAX_VALUE == number.doubleValue();
		// }
		// catch (NumberFormatException ex) {
		// System.out.println(ex.getMessage());
		// return Boolean.FALSE;
		// }
	}

	private static boolean one(Number number) {
		// if (number instanceof Long)
		// return number.doubleValue() == NumberUtils.LONG_ONE;
		// else if (number instanceof Integer)
		// return number.doubleValue() == NumberUtils.INTEGER_ONE;
		// else if (number instanceof Byte)
		// return number.doubleValue() == NumberUtils.BYTE_ONE;
		// else if (number instanceof Short)
		// return number.doubleValue() == NumberUtils.SHORT_ONE;
		// else if (number instanceof Double)
		// return number.doubleValue() == NumberUtils.DOUBLE_ONE;
		// else if (number instanceof Float)
		// return number.doubleValue() == NumberUtils.FLOAT_ONE;
		// else
		// throw new IllegalArgumentException();
		try {
			return NumberUtils.DOUBLE_ONE == number.doubleValue();
		}
		catch (NumberFormatException ex) {
			System.out.println(ex.getMessage());
			return Boolean.FALSE;
		}
	}

	private static boolean zero(Number number) {
		// if (number instanceof Long)
		// return number.doubleValue() == NumberUtils.LONG_ZERO;
		// else if (number instanceof Integer)
		// return number.doubleValue() == NumberUtils.INTEGER_ZERO;
		// else if (number instanceof Byte)
		// return number.doubleValue() == NumberUtils.BYTE_ZERO;
		// else if (number instanceof Short)
		// return number.doubleValue() == NumberUtils.SHORT_ZERO;
		// else if (number instanceof Double)
		// return number.doubleValue() == NumberUtils.DOUBLE_ZERO;
		// else if (number instanceof Float)
		// return number.doubleValue() == NumberUtils.FLOAT_ZERO;
		// else
		// throw new IllegalArgumentException();
		try {
			return NumberUtils.DOUBLE_ZERO == number.doubleValue();
		}
		catch (NumberFormatException ex) {
			System.out.println(ex.getMessage());
			return Boolean.FALSE;
		}
	}

	private static boolean minusOne(Number number) {
		// if (number instanceof Long)
		// return number.doubleValue() == NumberUtils.LONG_MINUS_ONE;
		// else if (number instanceof Integer)
		// return number.doubleValue() == NumberUtils.INTEGER_MINUS_ONE;
		// else if (number instanceof Byte)
		// return number.doubleValue() == NumberUtils.BYTE_MINUS_ONE;
		// else if (number instanceof Short)
		// return number.doubleValue() == NumberUtils.SHORT_MINUS_ONE;
		// else if (number instanceof Double)
		// return number.doubleValue() == NumberUtils.DOUBLE_MINUS_ONE;
		// else if (number instanceof Float)
		// return number.doubleValue() == NumberUtils.FLOAT_MINUS_ONE;
		// else
		// throw new IllegalArgumentException();
		try {
			return NumberUtils.DOUBLE_MINUS_ONE == number.doubleValue();
		}
		catch (NumberFormatException ex) {
			System.out.println(ex.getMessage());
			return Boolean.FALSE;
		}
	}

}
