package com.baidu.rigel.unique.utils;

/**
 * 客户类型
 * 
 * @author Anders Zhu
 */
public enum CustType {
	/**
	 * 普通企业（0）
	 */
	GENERAL_ENTERPRISE("普通企业", 0),
	/**
	 * 特俗企业（1）
	 */
	SPECIAL_ENTERPRISE("特殊企业", 1),
	/**
	 * 个人客户（2）
	 */
	PERSONAL_CUSTOMER("个人客户", 2),
	/**
	 * 广告代理（3）
	 */
	ADVERTISING_AGENCY("广告代理", 3);

	/**
	 * 枚举值
	 */
	private Integer value;
	/**
	 * 枚举标签
	 */
	private String label;

	/**
	 * 构造函数
	 * 
	 * @param label
	 *            枚举标签
	 * @param value
	 *            枚举值
	 */
	private CustType(String label, Integer value) {
		this.label = label;
		this.value = value;
	}

	/**
	 * 获得枚举标签
	 * 
	 * @return 返回枚举标签
	 */
	public String getLabel() {
		return this.label;
	}

	/**
	 * 获得枚举值
	 * 
	 * @return 返回枚举值
	 */
	public Integer getValue() {
		return this.value;
	}

	/**
	 * 获得枚举标签
	 * 
	 * @return 返回枚举标签
	 */
	@Override
	public String toString() {
		return this.label;
	}
}
