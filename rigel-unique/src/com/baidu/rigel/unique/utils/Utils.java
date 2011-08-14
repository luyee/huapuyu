package com.baidu.rigel.unique.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 工具类
 * 
 * @author Anders Zhu
 */
public class Utils {
	private static Log log = LogFactory.getLog(Utils.class);

	/**
	 * 根据指定的数字截取列表并返回结果
	 * 
	 * @param src
	 *            被截取列表
	 * @param count
	 *            数字
	 * @return 截取后的列表
	 */
	// TODO Anders Zhu : 重构
	public static List<Map<String, Object>> limitList(List<Map<String, Object>> srcList, int count) {

		if (isNull(srcList)) {
			log.warn("参数srcList为null");
			return new ArrayList<Map<String, Object>>(0);
		} else if (Utils.isLessThanZero(count)) {
			log.warn("参数count小于0");
			return new ArrayList<Map<String, Object>>(0);
		}

		if (srcList.size() > count)
			return srcList.subList(NumberUtils.INTEGER_ZERO, count);

		return srcList;
	}

	/**
	 * 如果第一个参数不为null和空字符串，则返回；否则继续判断第二个参数，依次类推，直至最后一个参数
	 * 
	 * @param values
	 *            可变多参数
	 * @return 第一个不为null和空字符串的参数
	 */
	public static String getFirstNotEmptyParam(String... values) {
		for (String value : values) {
			if (StringUtils.isNotEmpty(value)) {
				return value;
			}
		}
		return StringUtils.EMPTY;
	}

	/**
	 * 判断参数是否为null
	 * 
	 * <pre>
	 * Utils.isNull(null) = true;
	 * Utils.isNull(new ArrayList&lt;Long&gt;()) = false;
	 * </pre>
	 * 
	 * @param object
	 * @return 如果参数为null，返回true，否则返回false
	 */
	public static boolean isNull(Object object) {
		if (null == object)
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	}

	/**
	 * 判断参数是否不为null
	 * 
	 * <pre>
	 * Utils.isNotNull(null) = false;
	 * Utils.isNotNull(new ArrayList&lt;Long&gt;()) = true;
	 * </pre>
	 * 
	 * @param object
	 * @return 如果参数不为null，返回true，否则返回false
	 */
	public static boolean isNotNull(Object object) {
		return !isNull(object);
	}

	public static String escapeWildcard(String value) {
		return value.replaceAll(Constant.PERCENT_SIGN, Constant.PERCENT_SIGN_ESCAPE).replaceAll(Constant.UNDERLINE, Constant.UNDERLINE_ESCAPE);
	}

	public static String addWildcard(String value) {
		return Constant.PERCENT_SIGN + value + Constant.PERCENT_SIGN;
	}

	public static String addRightWildcard(String value) {
		return value + Constant.PERCENT_SIGN;
	}

	public static java.sql.Date getInvalidDate(int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -days);
		calendar.clear(Calendar.HOUR_OF_DAY);
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.SECOND);
		java.sql.Date invalidDate = new java.sql.Date(calendar.getTimeInMillis());
		return invalidDate;
	}

	public static boolean isEqualToZero(Number number) {
		if (number instanceof Long)
			return NumberUtils.INTEGER_ZERO == number.longValue();
		else if (number instanceof Short)
			return NumberUtils.SHORT_ZERO == number.shortValue();
		else if (number instanceof Byte)
			return NumberUtils.BYTE_ZERO == number.byteValue();
		else if (number instanceof Double)
			return NumberUtils.DOUBLE_ZERO == number.doubleValue();
		else if (number instanceof Float)
			return NumberUtils.FLOAT_ZERO == number.floatValue();
		else if (number instanceof Integer)
			return NumberUtils.INTEGER_ZERO == number.intValue();
		else
			// TODO Anders Zhu : 添加异常信息
			throw new IllegalArgumentException();
	}

	public static boolean isGreaterThanZero(Number number) {
		if (number instanceof Long)
			return NumberUtils.INTEGER_ZERO < number.longValue();
		else if (number instanceof Short)
			return NumberUtils.SHORT_ZERO < number.shortValue();
		else if (number instanceof Byte)
			return NumberUtils.BYTE_ZERO < number.byteValue();
		else if (number instanceof Double)
			return NumberUtils.DOUBLE_ZERO < number.doubleValue();
		else if (number instanceof Float)
			return NumberUtils.FLOAT_ZERO < number.floatValue();
		else if (number instanceof Integer)
			return NumberUtils.INTEGER_ZERO < number.intValue();
		else
			// TODO Anders Zhu : 添加异常信息
			throw new IllegalArgumentException();
	}

	public static boolean isLessThanZero(Number number) {
		if (number instanceof Long)
			return NumberUtils.INTEGER_ZERO > number.longValue();
		else if (number instanceof Short)
			return NumberUtils.SHORT_ZERO > number.shortValue();
		else if (number instanceof Byte)
			return NumberUtils.BYTE_ZERO > number.byteValue();
		else if (number instanceof Double)
			return NumberUtils.DOUBLE_ZERO > number.doubleValue();
		else if (number instanceof Float)
			return NumberUtils.FLOAT_ZERO > number.floatValue();
		else if (number instanceof Integer)
			return NumberUtils.INTEGER_ZERO > number.intValue();
		else
			// TODO Anders Zhu : 添加异常信息
			throw new IllegalArgumentException();
	}

	public static boolean isEqualGreaterThanZero(Number number) {
		return isEqualToZero(number) || isGreaterThanZero(number);
	}

	public static boolean isEqualLessThanZero(Number number) {
		return isEqualToZero(number) || isLessThanZero(number);
	}

	public static boolean isNotEqualToZero(Number number) {
		return !isEqualToZero(number);
	}

	public static boolean isEqualToOne(Number number) {
		if (number instanceof Long)
			return NumberUtils.INTEGER_ONE == number.longValue();
		else if (number instanceof Short)
			return NumberUtils.SHORT_ONE == number.shortValue();
		else if (number instanceof Byte)
			return NumberUtils.BYTE_ONE == number.byteValue();
		else if (number instanceof Double)
			return NumberUtils.DOUBLE_ONE == number.doubleValue();
		else if (number instanceof Float)
			return NumberUtils.FLOAT_ONE == number.floatValue();
		else if (number instanceof Integer)
			return NumberUtils.INTEGER_ONE == number.intValue();
		else
			// TODO Anders Zhu : 添加异常信息
			throw new IllegalArgumentException();
	}

	public static boolean isGreaterThanOne(Number number) {
		if (number instanceof Long)
			return NumberUtils.INTEGER_ONE < number.longValue();
		else if (number instanceof Short)
			return NumberUtils.SHORT_ONE < number.shortValue();
		else if (number instanceof Byte)
			return NumberUtils.BYTE_ONE < number.byteValue();
		else if (number instanceof Double)
			return NumberUtils.DOUBLE_ONE < number.doubleValue();
		else if (number instanceof Float)
			return NumberUtils.FLOAT_ONE < number.floatValue();
		else if (number instanceof Integer)
			return NumberUtils.INTEGER_ONE < number.intValue();
		else
			// TODO Anders Zhu : 添加异常信息
			throw new IllegalArgumentException();
	}

	public static boolean isLessThanOne(Number number) {
		if (number instanceof Long)
			return NumberUtils.INTEGER_ONE > number.longValue();
		else if (number instanceof Short)
			return NumberUtils.SHORT_ONE > number.shortValue();
		else if (number instanceof Byte)
			return NumberUtils.BYTE_ONE > number.byteValue();
		else if (number instanceof Double)
			return NumberUtils.DOUBLE_ONE > number.doubleValue();
		else if (number instanceof Float)
			return NumberUtils.FLOAT_ONE > number.floatValue();
		else if (number instanceof Integer)
			return NumberUtils.INTEGER_ONE > number.intValue();
		else
			// TODO Anders Zhu : 添加异常信息
			throw new IllegalArgumentException();
	}

	public static boolean isEqualGreaterThanOne(Number number) {
		return isEqualToOne(number) || isGreaterThanOne(number);
	}

	public static boolean isEqualLessThanOne(Number number) {
		return isEqualToOne(number) || isLessThanOne(number);
	}

	public static boolean isNotEqualToOne(Number number) {
		return !isEqualToOne(number);
	}

	public static boolean isEqualToMinusOne(Number number) {
		if (number instanceof Long)
			return NumberUtils.INTEGER_MINUS_ONE == number.longValue();
		else if (number instanceof Short)
			return NumberUtils.SHORT_MINUS_ONE == number.shortValue();
		else if (number instanceof Byte)
			return NumberUtils.BYTE_MINUS_ONE == number.byteValue();
		else if (number instanceof Double)
			return NumberUtils.DOUBLE_MINUS_ONE == number.doubleValue();
		else if (number instanceof Float)
			return NumberUtils.FLOAT_MINUS_ONE == number.floatValue();
		else if (number instanceof Integer)
			return NumberUtils.INTEGER_MINUS_ONE == number.intValue();
		else
			// TODO Anders Zhu : 添加异常信息
			throw new IllegalArgumentException();
	}

	public static boolean isGreaterThanMinusOne(Number number) {
		if (number instanceof Long)
			return NumberUtils.INTEGER_MINUS_ONE < number.longValue();
		else if (number instanceof Short)
			return NumberUtils.SHORT_MINUS_ONE < number.shortValue();
		else if (number instanceof Byte)
			return NumberUtils.BYTE_MINUS_ONE < number.byteValue();
		else if (number instanceof Double)
			return NumberUtils.DOUBLE_MINUS_ONE < number.doubleValue();
		else if (number instanceof Float)
			return NumberUtils.FLOAT_MINUS_ONE < number.floatValue();
		else if (number instanceof Integer)
			return NumberUtils.INTEGER_MINUS_ONE < number.intValue();
		else
			// TODO Anders Zhu : 添加异常信息
			throw new IllegalArgumentException();
	}

	public static boolean isLessThanMinusOne(Number number) {
		if (number instanceof Long)
			return NumberUtils.INTEGER_MINUS_ONE > number.longValue();
		else if (number instanceof Short)
			return NumberUtils.SHORT_MINUS_ONE > number.shortValue();
		else if (number instanceof Byte)
			return NumberUtils.BYTE_MINUS_ONE > number.byteValue();
		else if (number instanceof Double)
			return NumberUtils.DOUBLE_MINUS_ONE > number.doubleValue();
		else if (number instanceof Float)
			return NumberUtils.FLOAT_MINUS_ONE > number.floatValue();
		else if (number instanceof Integer)
			return NumberUtils.INTEGER_MINUS_ONE > number.intValue();
		else
			// TODO Anders Zhu : 添加异常信息
			throw new IllegalArgumentException();
	}

	public static boolean isEqualGreaterThanMinusOne(Number number) {
		return isEqualToMinusOne(number) || isGreaterThanMinusOne(number);
	}

	public static boolean isEqualLessThanMinusOne(Number number) {
		return isEqualToMinusOne(number) || isLessThanMinusOne(number);
	}

	public static boolean isNotEqualToMinusOne(Number number) {
		return !isEqualToMinusOne(number);
	}

	// 以下为辅助方法
	public static boolean isNullOrEqualToZero(Number number) {
		return isNull(number) || isEqualToZero(number);
	}

	public static boolean isNullOrEqualToOne(Number number) {
		return isNull(number) || isEqualToOne(number);
	}

	public static boolean isNullOrEqualToMinusOne(Number number) {
		return isNull(number) || isEqualToMinusOne(number);
	}

	public static boolean isNotNullAndEqualToZero(Number number) {
		return isNotNull(number) && isEqualToZero(number);
	}

	public static boolean isNotNullAndEqualToOne(Number number) {
		return isNotNull(number) && isEqualToOne(number);
	}

	public static boolean isNotNullAndEqualToMinusOne(Number number) {
		return isNotNull(number) && isEqualToMinusOne(number);
	}

	// TODO Anders Zhu : 重构
	/**
	 * 将list里面的记录的位置设置为1，返回整数
	 * 
	 * @param list
	 * @return
	 */
	public static int IntToBit(List list) {
		int ret = 0;
		for (Object pos : list) {
			ret += Math.pow(2, Long.valueOf(pos.toString()).intValue());
		}
		return ret;
	}
}
