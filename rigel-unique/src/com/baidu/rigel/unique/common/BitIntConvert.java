package com.baidu.rigel.unique.common;

import java.util.ArrayList;
import java.util.List;

public class BitIntConvert {

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

	/**
	 * 将bit转换成位置
	 * 
	 * @param list
	 * @return
	 */
	public static List<Long> BitToInt(int intValue) {
		List<Long> ret = new ArrayList<Long>();
		int i = 0;
		while (true) {
			if ((Double.valueOf(Math.pow(2, i)).intValue() | intValue) == intValue) {
				ret.add(Long.valueOf(i));
			} else if (Double.valueOf(Math.pow(2, i)).intValue() > intValue) {
				break;
			}
			i++;
		}
		return ret;
	}

	/**
	 * 判断bit中在指定位置是否有值
	 * 
	 * @param bit
	 * @param pos
	 * @return
	 */
	public static boolean isHave(int bit, int pos) {
		List<Long> listPos = BitToInt(bit);
		if (listPos.contains(Long.valueOf(pos))) {
			return true;
		}
		return false;
	}
}
