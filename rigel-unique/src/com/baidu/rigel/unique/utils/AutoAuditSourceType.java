package com.baidu.rigel.unique.utils;

/**
 * 自动审核结果来源类型
 * 
 * @author Anders Zhu 2011-7-7 上午11:46:54
 */
public enum AutoAuditSourceType {
	/**
	 * 自动审核结果来源：黑名单（0）
	 */
	BLACKLIST((short) 0),
	/**
	 * 自动审核结果来源：季节性备案客户名单（1）
	 */
	SEASON_FILE_CUST_LIST((short) 1),
	/**
	 * 自动审核结果来源：老客户白名单（2）
	 */
	SHIFEN_CUST_WHITELIST((short) 2),
	/**
	 * 自动审核结果来源：老客户（3）
	 */
	SHIFEN_OLD_CUST((short) 3),
	/**
	 * 自动审核结果来源：自有库（4）
	 */
	PERSONAL_LIB((short) 4),
	/**
	 * 自动审核结果来源：公共库（5）
	 */
	PUBLIC_LIB((short) 5);

	private Short value;

	private AutoAuditSourceType(Short value) {
		this.value = value;
	}

	public Short getValue() {
		return this.value;
	}
}
