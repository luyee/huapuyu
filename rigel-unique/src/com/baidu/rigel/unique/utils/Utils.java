package com.baidu.rigel.unique.utils;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
			return null;
		} else if (count < Constant.ZERO) {
			log.warn("参数count小于0");
			return null;
		}

		if (srcList.size() > count)
			return srcList.subList(Constant.ZERO, count);

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

	/**
	 * 判断参数是否大于0（>0）
	 * 
	 * <pre>
	 * Utils.isGreaterEqualThanZero(0) = false;
	 * Utils.isGreaterEqualThanZero(1) = true;
	 * Utils.isGreaterEqualThanZero(-1) = false;
	 * </pre>
	 * 
	 * @param value
	 * @return 如果参数大于0，返回true，否则返回false
	 */
	public static boolean isGreaterThanZero(int value) {
		if (value > Constant.ZERO)
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	}

	public static boolean isGreaterThanZero(long value) {
		if (value > Constant.ZERO)
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	}

	/**
	 * 判断参数是否小于等于0（<=0）
	 * 
	 * <pre>
	 * Utils.isGreaterEqualThanZero(0) = true;
	 * Utils.isGreaterEqualThanZero(1) = false;
	 * Utils.isGreaterEqualThanZero(-1) = true;
	 * </pre>
	 * 
	 * @param value
	 * @return 如果参数小于等于0，返回true，否则返回false
	 */
	public static boolean isLessEqualThanZero(int value) {
		return !isGreaterThanZero(value);
	}

	/**
	 * 判断参数是否小于0（<0）
	 * 
	 * <pre>
	 * Utils.isGreaterEqualThanZero(0) = false;
	 * Utils.isGreaterEqualThanZero(1) = false;
	 * Utils.isGreaterEqualThanZero(-1) = true;
	 * </pre>
	 * 
	 * @param value
	 * @return 如果参数小于0，则返回true，否则返回false
	 */
	public static boolean isLessThanZero(int value) {
		if (value < Constant.ZERO)
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	}

	public static boolean isLessThanZero(long value) {
		if (value < Constant.ZERO)
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	}

	/**
	 * 判断参数是否大于等于0（>=0）
	 * 
	 * <pre>
	 * Utils.isGreaterEqualThanZero(0) = true;
	 * Utils.isGreaterEqualThanZero(1) = true;
	 * Utils.isGreaterEqualThanZero(-1) = false;
	 * </pre>
	 * 
	 * @param value
	 * @return 如果参数大于等于0，则返回true，否则返回false
	 */
	public static boolean isGreaterEqualThanZero(int value) {
		return !isLessThanZero(value);
	}

	public static boolean isEqualZero(int value) {
		return Constant.ZERO == value;
	}

	public static boolean isEqualZero(long value) {
		return Constant.ZERO == value;
	}

	public static boolean isNotEqualZero(int value) {
		return !isEqualZero(value);
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

	public static boolean isEmpty(Object... objects) {
		return null == objects || objects.length <= Constant.ZERO;
	}

	public static boolean isEmpty(Long... objects) {
		return null == objects || objects.length <= Constant.ZERO;
	}

	public static boolean isNotEmpty(Object... objects) {
		return !isEmpty(objects);
	}

	public static boolean isNumber(String value) {
		try {
			new Double(value);
			return Boolean.TRUE;
		} catch (Exception e) {
			return Boolean.FALSE;
		}
	}

	public static boolean isEqualMinusOne(long value) {
		return Constant.MINUS_ONE == value;
	}

	public static boolean isEqualMinusOne(int value) {
		return Constant.MINUS_ONE == value;
	}

	public static boolean isEqualOne(long value) {
		return Constant.ZERO == value;
	}

	public static boolean isEqualOne(int value) {
		return Constant.ZERO == value;
	}
}
