package com.baidu.rigel.unique.utils;

/**
 * 启用/禁用类型
 * 
 * @author Anders Zhu
 */
public enum FlagType {
	/**
	 * 启用（0）
	 */
	ENABLE((short) 0),
	/**
	 * 禁用（1）
	 */
	DISENABLE((short) 1);

	/**
	 * 枚举值
	 */
	private Short value;

	/**
	 * 构造函数
	 * 
	 * @param label
	 *            枚举标签
	 * @param value
	 *            枚举值
	 */
	private FlagType(Short value) {
		this.value = value;
	}

	/**
	 * 获得枚举值
	 * 
	 * @return 返回枚举值
	 */
	public Short getValue() {
		return this.value;
	}
}
