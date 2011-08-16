package com.baidu.rigel.unique.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.baidu.rigel.service.usercenter.bean.Position;

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
	public static List<Map<String, Object>> limitList(List<Map<String, Object>> srcList, int count) {

		if (isNull(srcList)) {
			log.warn("srcList = null");
			return new ArrayList<Map<String, Object>>(0);
		} else if (isLessThanZero(count)) {
			log.warn("count <= 0");
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
		// if (number instanceof Long)
		// return NumberUtils.INTEGER_ZERO == number.longValue();
		// else if (number instanceof Short)
		// return NumberUtils.SHORT_ZERO == number.shortValue();
		// else if (number instanceof Byte)
		// return NumberUtils.BYTE_ZERO == number.byteValue();
		// else if (number instanceof Double)
		// return NumberUtils.DOUBLE_ZERO == number.doubleValue();
		// else if (number instanceof Float)
		// return NumberUtils.FLOAT_ZERO == number.floatValue();
		// else if (number instanceof Integer)
		// return NumberUtils.INTEGER_ZERO == number.intValue();
		// else
		// throw new IllegalArgumentException();
		try {
			return NumberUtils.DOUBLE_ZERO == number.doubleValue();
		} catch (NumberFormatException ex) {
			log.error(ex.getMessage());
			return Boolean.FALSE;
		}
	}

	public static boolean isGreaterThanZero(Number number) {
		// if (number instanceof Long)
		// return NumberUtils.INTEGER_ZERO < number.longValue();
		// else if (number instanceof Short)
		// return NumberUtils.SHORT_ZERO < number.shortValue();
		// else if (number instanceof Byte)
		// return NumberUtils.BYTE_ZERO < number.byteValue();
		// else if (number instanceof Double)
		// return NumberUtils.DOUBLE_ZERO < number.doubleValue();
		// else if (number instanceof Float)
		// return NumberUtils.FLOAT_ZERO < number.floatValue();
		// else if (number instanceof Integer)
		// return NumberUtils.INTEGER_ZERO < number.intValue();
		// else
		// throw new IllegalArgumentException();
		try {
			return NumberUtils.DOUBLE_ZERO < number.doubleValue();
		} catch (NumberFormatException ex) {
			log.error(ex.getMessage());
			return Boolean.FALSE;
		}
	}

	public static boolean isLessThanZero(Number number) {
		// if (number instanceof Long)
		// return NumberUtils.INTEGER_ZERO > number.longValue();
		// else if (number instanceof Short)
		// return NumberUtils.SHORT_ZERO > number.shortValue();
		// else if (number instanceof Byte)
		// return NumberUtils.BYTE_ZERO > number.byteValue();
		// else if (number instanceof Double)
		// return NumberUtils.DOUBLE_ZERO > number.doubleValue();
		// else if (number instanceof Float)
		// return NumberUtils.FLOAT_ZERO > number.floatValue();
		// else if (number instanceof Integer)
		// return NumberUtils.INTEGER_ZERO > number.intValue();
		// else
		// throw new IllegalArgumentException();
		try {
			return NumberUtils.DOUBLE_ZERO > number.doubleValue();
		} catch (NumberFormatException ex) {
			log.error(ex.getMessage());
			return Boolean.FALSE;
		}
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
		// if (number instanceof Long)
		// return NumberUtils.INTEGER_ONE == number.longValue();
		// else if (number instanceof Short)
		// return NumberUtils.SHORT_ONE == number.shortValue();
		// else if (number instanceof Byte)
		// return NumberUtils.BYTE_ONE == number.byteValue();
		// else if (number instanceof Double)
		// return NumberUtils.DOUBLE_ONE == number.doubleValue();
		// else if (number instanceof Float)
		// return NumberUtils.FLOAT_ONE == number.floatValue();
		// else if (number instanceof Integer)
		// return NumberUtils.INTEGER_ONE == number.intValue();
		// else
		// throw new IllegalArgumentException();
		try {
			return NumberUtils.DOUBLE_ONE == number.doubleValue();
		} catch (NumberFormatException ex) {
			log.error(ex.getMessage());
			return Boolean.FALSE;
		}
	}

	public static boolean isGreaterThanOne(Number number) {
		// if (number instanceof Long)
		// return NumberUtils.INTEGER_ONE < number.longValue();
		// else if (number instanceof Short)
		// return NumberUtils.SHORT_ONE < number.shortValue();
		// else if (number instanceof Byte)
		// return NumberUtils.BYTE_ONE < number.byteValue();
		// else if (number instanceof Double)
		// return NumberUtils.DOUBLE_ONE < number.doubleValue();
		// else if (number instanceof Float)
		// return NumberUtils.FLOAT_ONE < number.floatValue();
		// else if (number instanceof Integer)
		// return NumberUtils.INTEGER_ONE < number.intValue();
		// else
		// throw new IllegalArgumentException();
		try {
			return NumberUtils.DOUBLE_ONE < number.doubleValue();
		} catch (NumberFormatException ex) {
			log.error(ex.getMessage());
			return Boolean.FALSE;
		}
	}

	public static boolean isLessThanOne(Number number) {
		// if (number instanceof Long)
		// return NumberUtils.INTEGER_ONE > number.longValue();
		// else if (number instanceof Short)
		// return NumberUtils.SHORT_ONE > number.shortValue();
		// else if (number instanceof Byte)
		// return NumberUtils.BYTE_ONE > number.byteValue();
		// else if (number instanceof Double)
		// return NumberUtils.DOUBLE_ONE > number.doubleValue();
		// else if (number instanceof Float)
		// return NumberUtils.FLOAT_ONE > number.floatValue();
		// else if (number instanceof Integer)
		// return NumberUtils.INTEGER_ONE > number.intValue();
		// else
		// throw new IllegalArgumentException();
		try {
			return NumberUtils.DOUBLE_ONE > number.doubleValue();
		} catch (NumberFormatException ex) {
			log.error(ex.getMessage());
			return Boolean.FALSE;
		}
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
		// if (number instanceof Long)
		// return NumberUtils.INTEGER_MINUS_ONE == number.longValue();
		// else if (number instanceof Short)
		// return NumberUtils.SHORT_MINUS_ONE == number.shortValue();
		// else if (number instanceof Byte)
		// return NumberUtils.BYTE_MINUS_ONE == number.byteValue();
		// else if (number instanceof Double)
		// return NumberUtils.DOUBLE_MINUS_ONE == number.doubleValue();
		// else if (number instanceof Float)
		// return NumberUtils.FLOAT_MINUS_ONE == number.floatValue();
		// else if (number instanceof Integer)
		// return NumberUtils.INTEGER_MINUS_ONE == number.intValue();
		// else
		// throw new IllegalArgumentException();
		try {
			return NumberUtils.DOUBLE_MINUS_ONE == number.doubleValue();
		} catch (NumberFormatException ex) {
			log.error(ex.getMessage());
			return Boolean.FALSE;
		}
	}

	public static boolean isGreaterThanMinusOne(Number number) {
		// if (number instanceof Long)
		// return NumberUtils.INTEGER_MINUS_ONE < number.longValue();
		// else if (number instanceof Short)
		// return NumberUtils.SHORT_MINUS_ONE < number.shortValue();
		// else if (number instanceof Byte)
		// return NumberUtils.BYTE_MINUS_ONE < number.byteValue();
		// else if (number instanceof Double)
		// return NumberUtils.DOUBLE_MINUS_ONE < number.doubleValue();
		// else if (number instanceof Float)
		// return NumberUtils.FLOAT_MINUS_ONE < number.floatValue();
		// else if (number instanceof Integer)
		// return NumberUtils.INTEGER_MINUS_ONE < number.intValue();
		// else
		// throw new IllegalArgumentException();
		try {
			return NumberUtils.DOUBLE_MINUS_ONE < number.doubleValue();
		} catch (NumberFormatException ex) {
			log.error(ex.getMessage());
			return Boolean.FALSE;
		}
	}

	public static boolean isLessThanMinusOne(Number number) {
		// if (number instanceof Long)
		// return NumberUtils.INTEGER_MINUS_ONE > number.longValue();
		// else if (number instanceof Short)
		// return NumberUtils.SHORT_MINUS_ONE > number.shortValue();
		// else if (number instanceof Byte)
		// return NumberUtils.BYTE_MINUS_ONE > number.byteValue();
		// else if (number instanceof Double)
		// return NumberUtils.DOUBLE_MINUS_ONE > number.doubleValue();
		// else if (number instanceof Float)
		// return NumberUtils.FLOAT_MINUS_ONE > number.floatValue();
		// else if (number instanceof Integer)
		// return NumberUtils.INTEGER_MINUS_ONE > number.intValue();
		// else
		// throw new IllegalArgumentException();
		try {
			return NumberUtils.DOUBLE_MINUS_ONE > number.doubleValue();
		} catch (NumberFormatException ex) {
			log.error(ex.getMessage());
			return Boolean.FALSE;
		}
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

	public static boolean isNotNullAndGeaterThanZero(Number number) {
		return isNotNull(number) && isGreaterThanZero(number);
	}

	// TODO Anders Zhu : 不知道干嘛用的
	/**
	 * 将list里面的记录的位置设置为1，返回整数
	 * 
	 * @param list
	 * @return
	 */
	public static int IntToBit(List<Long> list) {
		int returnValue = NumberUtils.INTEGER_ZERO;
		for (Long pos : list)
			returnValue += Math.pow(Constant.DOUBLE_TWO, pos);
		return returnValue;
	}

	/**
	 * 转换为前端树状接口格式，如有不明请参考fe接口文档
	 * 
	 * ps: If it works properly, it is written by yanbing, otherwise it is written by zhaobing
	 * 
	 * @param posid
	 *            根节点id
	 * @param pl
	 *            岗位信息列表
	 * @param exType
	 *            需要过滤的postype
	 * @return
	 */
	public static List<Map<String, Object>> trans2FeList(Long posid, List<Position> pl, Short exType) {

		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();

		if (posid == null || pl == null)
			return ret;

		Collections.sort(pl, new PosComparator());

		Position root = null;
		Map<Long, List<Position>> pid2pos = new HashMap<Long, List<Position>>();
		for (Position pos : pl) {
			if (pos == null)
				continue;

			if (exType != null && exType.equals(pos.getPostype()))
				continue;

			if (posid.equals(pos.getPosid())) {
				root = pos;
				continue;
			}

			Long pid = pos.getParentid();
			List<Position> clist = pid2pos.get(pid);

			if (clist == null) {
				clist = new ArrayList<Position>();
				pid2pos.put(pid, clist);
			}
			clist.add(pos);
		}

		if (root == null)
			return ret;

		Stack<Position> stack = new Stack<Position>();
		stack.push(root);

		while (!stack.isEmpty()) {
			Position pos = stack.pop();

			Map<String, Object> mp = new HashMap<String, Object>();

			mp.put("value", pos.getPosid());
			mp.put("parent_id", pos.getParentid());
			mp.put("text", pos.getPosname());

			ret.add(mp);

			List<Position> clist = pid2pos.get(pos.getPosid());
			if (clist != null) {
				for (Position cpos : clist) {
					stack.push(cpos);
				}
			}
		}

		ret.get(0).put("parent_id", false);

		return ret;
	}

	private static class PosComparator implements Comparator<Position> {

		public int compare(Position o1, Position o2) {

			int id1 = o1.getPosid() == null ? 0 : o1.getPosid().intValue();
			int id2 = o2.getPosid() == null ? 0 : o2.getPosid().intValue();

			return id2 - id1;
		}

	}
}
