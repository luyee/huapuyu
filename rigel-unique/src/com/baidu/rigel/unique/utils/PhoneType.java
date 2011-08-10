package com.baidu.rigel.unique.utils;

/**
 * 电话类型
 * 
 * @author Anders Zhu
 */
public enum PhoneType {
	/**
	 * 座机（0）
	 */
	TELEPHONE((short) 0),

	/**
	 * 手机（1）
	 */
	MOBILE((short) 1);

	private Short value;

	private PhoneType(Short value) {
		this.value = value;
	}

	public Short getValue() {
		return this.value;
	}
}
